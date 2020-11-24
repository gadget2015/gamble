import {Spelbolagservice} from './spelbolagservice';
import {Logger} from 'winston';

/**
* BFF layer for do operations on spelbolag regestry.
*/
class BFF {
    logger: Logger;

    constructor(logger: Logger) {
        this.logger = logger;
    }
    /**
    * Skapar ett Promise som hämtar presentationsdata för Spelbolags sidan.
    */
    async getInitialVyForSpelbolag() {
        const bffPromise = new Promise(async (resolve, reject) => {
            const spelbolagservice = new Spelbolagservice(this.logger);

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
            const spelbolagservice = new Spelbolagservice(this.logger);
            try{
                // Hämtar alla transaktioner
                const transaktionerResult = await spelbolagservice.getTransactions(kontonummer);
                let transaktioner = transaktionerResult['queryResult'];
                this.formateraTransaktionsDatum(transaktioner);

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
                this.beraknaSaldoPerTransaktion(transaktioner);
                this.sorteraTransaktionerFallande(transaktioner);

                resolve({bffResult: {'saldo': sum, transaktioner: transaktioner}});
            } catch(e) {
                reject('Kan inte hämta transaktioner för givet konto.' + JSON.stringify(e));
            }
        });

        return bffPromise;
    }

    /**
    * Skapar ett promise för Mitt Saldo sidan.
    */
    async getInitialVyForMittsaldo(userid : string) {
        const bffPromise = new Promise(async (resolve, reject) => {
            const spelbolagservice = new Spelbolagservice(this.logger);

            try {
                // Hämtar totalt saldo för spelaren
                const spelareResult = await spelbolagservice.getSpelare(userid);
                const spelare = spelareResult['queryResult'];

                const kontoResult = await spelbolagservice.getKontoByID(spelare[0].konto_id);
                const konto = kontoResult['queryResult'];
                const kontonummer = konto[0]['kontonr'];

                 // Räknar ut totalt saldo
                const saldo = await spelbolagservice.getSaldo(kontonummer);
                let bffResult = {saldo: saldo};

                // Hämtar alla transaktioner.
                const transaktionerResult = await spelbolagservice.getTransactions(kontonummer);
                const transaktioner = transaktionerResult['queryResult'];

                 // Räknar ut saldo för varje transaktion.
                this.beraknaSaldoPerTransaktion(transaktioner);
                this.formateraTransaktionsDatum(transaktioner);
                this.sorteraTransaktionerFallande(transaktioner);

                bffResult['transaktioner'] = transaktioner;

                resolve({bffResult: bffResult});
            } catch(e) {
                reject('Kan inte hämta information till Mitt Saldo sidan.');
            }
        });

        return bffPromise;
    }

    /**
    * Sorterar transaktioner med datum med fallande datum, dvs. 2020-12-04 kommer före 2020-11-08.
    * Används för att få en bra presentation.
    */
    private sorteraTransaktionerFallande(transaktioner) {
        transaktioner.sort(function(a,b) {
            let dateA = Date.parse(a.tid);
            let dateB = Date.parse(b.tid);
            return (dateB - dateA);
        });
    }

    /**
    * Formaterar datum för transaktioner.
    */
    private formateraTransaktionsDatum(transaktioner) {
        let i = 0;

        for (i =0; i < transaktioner.length; i++) {
            let transaktionerDate = new Date(transaktioner[i].tid).toISOString().slice(0,10);
            transaktioner[i].datum = transaktionerDate;
        }
    }

    /**
    * Räknar ut saldo för varje transaktion.
    */
    private beraknaSaldoPerTransaktion(transaktioner){
        let saldo = 0;
        let i;

        for (i =0; i < transaktioner.length; i++) {
            let transaktionensSaldo = 0;
            transaktionensSaldo += transaktioner[i].debet - transaktioner[i].kredit;
            saldo = saldo + transaktionensSaldo;
            transaktioner[i].saldo = saldo;
        };
    }

   /**
    * Skapar ett promise för den initiala vyn i Administrationsidan.
    */
    async getInitialVyForAdministration(userid : string) {
        const bffPromise = new Promise(async (resolve, reject) => {
            const spelbolagservice = new Spelbolagservice(this.logger);

            try {
                // Hämtar saldo för Spelbolaget som spelaren är
                // administratör för.
                let spelareResult = await spelbolagservice.getSpelare(userid);
                let spelare = spelareResult['queryResult'];
                const administratorforspelbolag_id = spelare[0]['administratorforspelbolag_id'];

                const allaSpelbolagResult = await spelbolagservice.getAllaSpelbolag();
                const allaSpelbolag = allaSpelbolagResult['queryResult'];
                let i, spelbolag;

                for (i = 0; i< allaSpelbolag.length; i++) {
                    if(allaSpelbolag[i]['ID'] === administratorforspelbolag_id) {
                        spelbolag = allaSpelbolag[i];
                    }
                }
                let bffResult = {};
                bffResult['namn'] = spelbolag.namn;
                bffResult['insatsperomgang'] = spelbolag.insatsperomgang;

                 // Räknar ut totalt saldo
                const kontoResult = await spelbolagservice.getKontoByID(spelbolag.konto_id);
                const konto = kontoResult['queryResult'];
                const kontonummer = konto[0]['kontonr'];
                const saldo = await spelbolagservice.getSaldo(kontonummer);
                bffResult['saldo'] = saldo;
                bffResult['kontonummer'] = kontonummer;

                // Hämtar alla spelare som ingår i spelbolaget och lägger in saldo per spelare.
                const allaSpelare = await this.hamtaAllaSpelareMedSaldo(spelbolag.ID);
                bffResult['spelarInfo'] = allaSpelare;

                // Returnerar data till vyn.
                resolve({bffResult: bffResult});
            } catch(e) {
                reject('Kan inte hämta information till Administrationsidan.');
            }
        });

        return bffPromise;
    }

    /**
    * Hämtar transaktioner för givet kontonummer.
    */
    addTransaktion(beskrivning: string, kredit: string, debet: string, kontonummer : string) {
        const bffPromise = new Promise(async (resolve, reject) => {
            const spelbolagservice = new Spelbolagservice(this.logger);
            try{
                // Hämtar alla transaktioner
                const transaktionerResult = await spelbolagservice.addTransaktion(beskrivning, kredit, debet, kontonummer);
                let transaktioner = transaktionerResult['queryResult'];
                let affectedRows = transaktioner.affectedRows;

                resolve({bffResult: {'affectedRows': affectedRows}});
            } catch(e) {
                reject('Kan inte hämta transaktioner för givet konto.' + JSON.stringify(e));
            }
        });

        return bffPromise;
    }

    /**
    * Ta betalt av alla spelare i givet spelbolag.
    */
    taBetaltAvSpelare(spelbolagsnamn : string) {
        const bffPromise = new Promise(async (resolve, reject) => {
            const spelbolagservice = new Spelbolagservice(this.logger);
            try{
                // Tar betalt av alla spelare som ingår i spelbolaget.
                const now = new Date();
                const result = await spelbolagservice.taBetaltForEnOmgang(spelbolagsnamn, now.toISOString());

                // Räkna ut nytt saldo.
                const spelbolagResult = await spelbolagservice.getSpelbolag(spelbolagsnamn);
                const spelbolag = spelbolagResult['queryResult'][0];
                const kontoResult = await spelbolagservice.getKontoByID(spelbolag.konto_id);
                const konto = kontoResult['queryResult'];
                const kontonummer = konto[0]['kontonr'];
                const saldo = await spelbolagservice.getSaldo(kontonummer);
                let bffResult = {saldo:0, spelarInfo: []};
                bffResult.saldo = saldo;

                // Hämta alla spelarna med uppdaterat saldo på.
                const allaSpelare = await this.hamtaAllaSpelareMedSaldo(spelbolag.ID);
                bffResult.spelarInfo = allaSpelare;

                resolve({bffResult: bffResult});
            } catch(e) {
                reject('Kan inte hämta transaktioner för givet konto.' + JSON.stringify(e));
            }
        });

        return bffPromise;
    }

    /**
    * Utility metod för att hämta alla spelare i givet spelbolag, och även beräkna saldo för respektive spelare.
    */
    private async hamtaAllaSpelareMedSaldo(spelbolagid) {
        const spelbolagservice = new Spelbolagservice(this.logger);
        const spelareResult = await spelbolagservice.getAllaSpelareForSpelbolag(spelbolagid);
        const spelare = spelareResult['queryResult'];
        let spelarInfo = [];
        let i;

        for(i=0; i<spelare.length; i++) {
            const userid = spelare[i]['userid'];
            const kontoResult = await spelbolagservice.getKontoByID(spelare[i]['konto_id']);
            const konto = kontoResult['queryResult'];
            const kontonummer = konto[0]['kontonr'];
            const spelarSaldo = await spelbolagservice.getSaldo(kontonummer);

            const spelareBffData = { userid: userid, saldo: spelarSaldo, kontonummer: kontonummer};
            spelarInfo.push(spelareBffData);
        }

        return spelarInfo;
    }
}

export {BFF};