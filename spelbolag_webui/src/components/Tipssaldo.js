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
            const notAuthenticated = vydata['error'];
            if (notAuthenticated !== null) {
                setMessage('Du måste logga in för att kunna se ditt saldo.');
            } else {
                setTransaktioner(vydata['data']['transaktioner']);
                setSaldo(vydata['data']['saldo']);
                console.log('saldo=' + vydata['data']['saldo']);
            }
        }, (failed) => {
            alert('Network connection error when calling REST API, status code = ' + failed);
        });
    }, [rerun]);

    return (
        <div>
        <div className="message">{message}</div>
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
                        return (<tr>
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
      );
}

export {Tipssaldo};