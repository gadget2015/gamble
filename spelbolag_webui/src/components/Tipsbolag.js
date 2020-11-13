import React from 'react';
import {useEffect} from 'react';
import {useState} from 'react';
import {BFF} from './../service/BFF';
import './Tipsbolag.css';

function Tipsbolag() {
    const [rerun] = useState(false);  // Ska bara hämta data en gång.
    const [spelbolag, setSpelbolag] = useState([]);
    const [transaktioner, setTransaktioner] = useState([]);
    const [saldo, setSaldo] = useState(0);

    // Laddar in vy data.
    useEffect( () => {
        const bffService = new BFF();
        bffService.spelbolagStartSida().then((vydata) => {
            setSpelbolag(vydata['data']);
        }, (failed) => {
            alert('Network connection error when calling REST API, status code = ' + failed);
        });
    }, [rerun]);

    const visaTransaktioner = function(kontonummer) {
        const bffService = new BFF();
                bffService.transaktionerForSpelboalg(kontonummer).then((vydata) => {
                    setTransaktioner(vydata['data']['transaktioner']);
                    setSaldo(vydata['data']['saldo']);
                }, (failed) => {
                    alert('Network connection error when calling REST API, status code = ' + failed);
                });
    };

    return (
        <div>
        Här är alla aktiva spelbolag.
         <ul>
            {

                spelbolag.map((currentValue, index) => {
                        return (<li key={index}>
                            <div onClick={() => visaTransaktioner(currentValue['kontonummer'])} className="linkclick">{currentValue['namn']}</div>Insats {currentValue['insatsperomgang']} kr per omgång, och tipssaldo {currentValue['saldo']} kr.</li>
                            );
                        })
            }
         </ul>

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
      );
}

export {Tipsbolag};