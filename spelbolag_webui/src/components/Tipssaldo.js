import React from 'react';
import {useEffect} from 'react';
import {useState} from 'react';
import {BFF} from './../service/BFF';
import './Tipssaldo.css';

function Tipssaldo() {
    const [rerun] = useState(false);  // Ska bara hämta data en gång.
    const [transaktioner, setTransaktioner] = useState([]);
    const [saldo, setSaldo] = useState(0);
    const [message, setMessage] = useState('');

    // Laddar in vy data.
    useEffect( () => {
        const bffService = new BFF();

        bffService.tipssaldoStartSida().then((vydata) => {
            const authenticated = vydata['success'];

            if (authenticated === false) {
                setMessage('Du måste logga in för att kunna se ditt saldo.');
            } else {
                setTransaktioner(vydata['data']['transaktioner']);
                setSaldo(vydata['data']['saldo']);
            }
        }, (failed) => {
            alert('Network connection error when calling REST API, status code = ' + failed);
        });
    }, [rerun]);

    const transaktionerUI = function() {
        return (<div>
        Här är alla dina transaktioner.

         Saldo: <b>{saldo} kr</b>.<br></br><br></br>
         <b>Transaktioner:</b>
         <table>
            <thead>
                <tr>
                    <th>Datum</th>
                    <th>Beskrivning</th>
                    <th>Kredit</th>
                    <th>Debet</th>
                    <th>Saldo</th>
                </tr>
            </thead>
            <tbody>
                {
                    transaktioner.map((currentValue) => {
                        return (<tr key={currentValue['ID']}>
                          <td>{currentValue['datum']}</td>
                          <td>{currentValue['beskrivning']}</td>
                          <td>{currentValue['kredit']}</td>
                          <td>{currentValue['debet']}</td>
                          <td>{currentValue['saldo']}</td>
                        </tr>
                        );
                    })
                }
              </tbody>
         </table>
         </div>
        )
    };

    const error = function() {
        return (<div>
                <div className="message">{message}</div>
            </div>
        );
    };

    // Visar olika UI delar beroende på om man är inloggad eller inte = conditional rendering.
    let uiContent;

    if (message !== null) {
        uiContent = error();
        setMessage(null);   // Visar bara meddelandet en gång.
    } else {
        uiContent = transaktionerUI();
    }

    return (
        <div>
            {uiContent}
        </div>
      );
}

export {Tipssaldo};