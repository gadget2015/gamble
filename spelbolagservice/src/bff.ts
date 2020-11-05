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
}

export {BFF};