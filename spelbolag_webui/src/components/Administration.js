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
    const [beskrivning, setBeskrivning] = useState('');
    const [saldo, setSaldo] = useState(0);
    const [spelare, setSpelare] = useState([]);

    // Laddar in vy data - bara en gång.
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
                setSaldo(vydata['data']['saldo']);
                setSpelare(vydata['data']['spelarInfo']);
                let now = new Date();
                setDatum(now.toISOString());
                setMessage(null);
            }
        }, (failed) => {
            alert('Network connection error when calling REST API, status code = ' + failed);
        });
    }, [rerun]);

    const laggTillTransaktion = function (event) {
        console.log('Lägger till en transaktion.');
        event.preventDefault();
    }

    const taBetaltAvAllaSpelare = function (event) {
        console.log('Ta betalt av alla spelare.');
        event.preventDefault();
    }

    const visaTransaktioner = function (userid) {
        console.log('Visa transaktioner för ' + userid);
    }

    //
    // Presentation logik
    //
    const error = function() {
        return (<div>
                <div className="message">{message}</div>
            </div>
        );
    };

    const inloggadContent = function () {
        return (<div>
            <b>Administrera spelbolag:</b> {namn}.<br/>
            <b>Insats per omgång:</b> {insatsperomgang} kr.<br/>
            <b>Saldo:</b> {saldo} kr.<br/>
            ----------------------------------------------------<br/>
            <b>Ny transaktion</b><br/>
            <form onSubmit={laggTillTransaktion}>
                <label>Datum:<input type="text" name='datum' defaultValue={datum} onChange={event => setDatum(event.target.value)} />
                (ISO 8601 datumformat)</label><br/>
                <label>Beskrivning:<input type="text" name='beskrivning' maxLength='50' style = {{width: 400}} defaultValue={beskrivning} onChange={event => setBeskrivning(event.target.value)} />
                </label><br/>
                <label>Kredit:<input type="text" maxLength='5' style = {{width: 50}} defaultValue={kredit} name='kredit' onChange={event => setKredit(event.target.value)} />
                </label><br/>
                <label>Debet:<input type="text" maxLength='5' style = {{width: 50}}  defaultValue={debet} name='debet' onChange={event => setDebet(event.target.value)} /></label><br/>
               <input type="submit" value="Lägg till transaktion" />
            </form>
            ----------------------------------------------------<br/>
            <b>Ta betalt av alla spelare</b><br/>
            <form onSubmit={taBetaltAvAllaSpelare}>
                Det kommer att dras {insatsperomgang} kr ifrån varje spelare som är med i spelbolag {namn}.<br/>
               <input type="submit" value="Ta betalt" />
            </form>
             ----------------------------------------------------<br/>
             <b>Alla spelare som ingår i spelbolag</b><br/>
            <ul>
            {
                spelare.map((currentValue, index) => {
                        return (<li key={index}>
                            <div onClick={() => visaTransaktioner(currentValue['userid'])} className="linkclick">{currentValue['userid']}</div>Saldo {currentValue['saldo']} kr.</li>
                            );
                        })
            }
         </ul>
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