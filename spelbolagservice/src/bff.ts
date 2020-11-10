import {Spelbolagservice} from './spelbolagservice';
/**
* BFF layer for do operations on spelbolag regestry.
*/
class BFF {

    /**
    * Skapar ett Promise som hämtar presentationsdata.
    */
    async getInitialVyForSpelbolag() {
        const bffPromise = new Promise(async (resolve, reject) => {
            const spelbolagservice = new Spelbolagservice();

            try {
                // Hämtar alla spelbolag
                const spelbolagResult = await spelbolagservice.getAllaSpelbolag();
                const spelbolag = spelbolagResult['queryResult'];

                // Hämtar saldo för alla spelbolag
                let bffResult = spelbolag;
                let i= 0;
                for (i = 0; i < bffResult.length; i++ ) {
                    const konto = await spelbolagservice.getKontoByID(bffResult[i].konto_id);
                    const kontonr = konto['queryResult'][0]['kontonr'];
                    const saldo = await spelbolagservice.getSaldo(kontonr);
                    bffResult[i].saldo = saldo;
                    bffResult[i].kontonummer = kontonr;
                 };

                // Hämtar antal spelare för varje spelbolag.
                i= 0;
                for (i = 0; i < bffResult.length; i++ ) {
                    const spelare = await spelbolagservice.getAllaSpelareForSpelbolag(bffResult[i].ID);
                    bffResult[i].antalSpelare = spelare['queryResult'].length;
                }

                resolve({bffResult: spelbolag});
            } catch(e) {
                reject('Kan inte hämta alla spelbolag.');
            }
        });

        return bffPromise;
    }

    /**
    * Hämtar transaktioner för givet kontonummer.
    */
    transaktionerForEttKonto(kontonummer : string) {
        const bffPromise = new Promise(async (resolve, reject) => {
            const spelbolagservice = new Spelbolagservice();
            try{
                // Hämtar alla transaktioner
                const transaktionerResult = await spelbolagservice.getTransactions(kontonummer);
                let transaktioner = transaktionerResult['queryResult'];
                let i = 0;

                for (i =0; i < transaktioner.length; i++) {
                    let transaktionerDate = new Date(transaktioner[i].tid).toISOString().slice(0,10);
                    transaktioner[i].datum = transaktionerDate;
                }

                // Sorterar transaktioner med datum med stigande datum, dvs. 2020-11-04 kommer före 2020-11-08.
                // Används för att beräkna ifrån början till fram till idag/senast tid,
                transaktioner.sort(function(a,b) {
                    let dateA = Date.parse(a.tid);
                    let dateB = Date.parse(b.tid);
                    return (dateA - dateB);
                });

                // Räknar ut totalt saldo
                const sum = await spelbolagservice.getSaldo(kontonummer);

                // Räknar ut saldo för varje transaktion.
                let saldo = 0;
                for (i =0; i < transaktioner.length; i++) {
                    let transaktionensSaldo = 0;
                    transaktionensSaldo += transaktioner[i].debet - transaktioner[i].kredit;
                    saldo = saldo + transaktionensSaldo;
                    transaktioner[i].saldo = saldo;
                };

                // Sorterar transaktioner med datum med fallande datum, dvs. 2020-12-04 kommer före 2020-11-08.
                // Används för att få en bra presentation.
                transaktioner.sort(function(a,b) {
                    let dateA = Date.parse(a.tid);
                    let dateB = Date.parse(b.tid);
                    return (dateB - dateA);
                });

                resolve({bffResult: {'saldo': sum, transaktioner: transaktioner}});
            } catch(e) {
                reject('Kan inte hämta transaktioner för givet konto.' + JSON.stringify(e));
            }
        });

        return bffPromise;
    }
}

export {BFF};