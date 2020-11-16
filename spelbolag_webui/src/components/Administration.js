import React from 'react';
import {useState} from 'react';
import {useEffect} from 'react';
import './Administration.css';
import {BFF} from './../service/BFF';

function Administration() {
    const [message, setMessage] = useState('');
    const [rerun] = useState(false);  // Ska bara hämta data en gång.
    const [namn, setNamn] = useState();
    const [insatsperomgang, setInsatsperomgang] = useState(0);
    const [datum, setDatum] = useState();
    const [kredit, setKredit] = useState(0);
    const [debet, setDebet] = useState(0);

    // Laddar in vy data.
    useEffect( () => {
        const bffService = new BFF();

        bffService.administrationStartSida().then((vydata) => {
            const authenticated = vydata['success'];

            if (authenticated === 'false') {
                setMessage('Du måste logga in för att kunna se ditt saldo.');
            } else {
            console.log('retData=' + JSON.stringify(vydata));
                setNamn(vydata['data']['namn']);
                setInsatsperomgang(vydata['data']['insatsperomgang']);
                setDatum('2020-11-16T05:08:20.000Z');
                setMessage(null);
            }
        }, (failed) => {
            alert('Network connection error when calling REST API, status code = ' + failed);
        });
    }, [rerun]);

    const error = function() {
        return (<div>
                <div className="message">{message}</div>
            </div>
        );
    };

    const laggTillTransaktion = function (event) {
        console.log('Lägger till en transaktion.');
        event.preventDefault();
    }

    const inloggadContent = function () {
        return (<div>
            <b>Administrera spelbolag:</b> {namn}.<br/>
            <b>Insats per omgång:</b> {insatsperomgang} kr.<br/>
            ----------------------------------------------------<br/>
            <b>Ny transaktion:</b><br/>
            <form onSubmit={laggTillTransaktion}>
                <label>Datum:<input type="text" name='datum' defaultValue={datum} onChange={event => setDatum(event.target.value)} />
                </label><br/>
                <label>Kredit:<input type="text" defaultValue={kredit} name='kredit' onChange={event => setKredit(event.target.value)} />
                </label><br/>
                <label>Debet:<input type="text" defaultValue={debet} name='debet' onChange={event => setDebet(event.target.value)} /></label>
               <input type="submit" value="Lägg till transaktion" />
            </form>
            </div>
        );
    }
    // Visar olika UI delar beroende på om man är inloggad eller inte = conditional rendering.
    let uiContent;

    if (message !== null) {
        uiContent = error();
    } else {
        uiContent = inloggadContent();
    }

    return (
        <div>
             {uiContent}
        </div>
    );
}

export { Administration };