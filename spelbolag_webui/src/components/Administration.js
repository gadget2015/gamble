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
    const [spelbolagetsKontonummer, setSpelbolagetsKontonummer] = useState();
    const [kredit, setKredit] = useState(0);
    const [debet, setDebet] = useState(0);
    const [beskrivning, setBeskrivning] = useState('');
    const [saldo, setSaldo] = useState(0);
    const [spelare, setSpelare] = useState([]);
    const [transaktioner, setTransaktioner] = useState([]);
    const [valdSeplare, setValdSpelare] = useState();
    const [spelareDatum, setSpelareDatum] = useState();
    const [spelareBeskrivning, setSpelareBeskrivning] = useState('');
    const [spelareKredit, setSpelareKredit] = useState(0);
    const [spelareDebet, setSpelareDebet] = useState(0);

    // Laddar in vy data - bara en gång.
    useEffect( () => {
        const bffService = new BFF();

        bffService.administrationStartSida().then((vydata) => {
            const authenticated = vydata['success'];

            if (authenticated === 'false') {
                setMessage('Du måste logga in för att kunna se ditt saldo.');
            } else {
                setNamn(vydata['data']['namn']);
                setInsatsperomgang(vydata['data']['insatsperomgang']);
                setSaldo(vydata['data']['saldo']);
                setSpelbolagetsKontonummer(vydata['data']['kontonummer']);
                setSpelare(vydata['data']['spelarInfo']);
                setSpelareDatum(new Date().toISOString());
                setMessage(null);
            }
        }, (failed) => {
            alert('Network connection error when calling REST API, status code = ' + failed);
        });
    }, [rerun]);

    const laggTillTransaktionForSpelbolag = function (event) {
        const bffService = new BFF();
        bffService.laggTillTransaktion(beskrivning, kredit, debet, spelbolagetsKontonummer).then((vydata) => {
            const authenticated = vydata['success'];

            if (authenticated === 'false') {
                setMessage('Du måste vara administratör för att använda denna sidan.');
            } else {
                // räknar ut nytt saldo.
                const nyttSaldo = Number(saldo) + Number(debet) - Number(kredit);
                setSaldo(nyttSaldo);

                // reset values
                setBeskrivning('');
                setKredit(0);
                setDebet(0);
                setMessage(null);
            }
        }, (failed) => {
            setMessage('Network connection error when calling REST API, status code = ' + failed);
        });

        event.preventDefault();
    }

    const laggTillTransaktionForSpelare = function (event) {
        const bffService = new BFF();

        bffService.laggTillTransaktionForSpelare(spelareDatum, spelareBeskrivning, spelareKredit, spelareDebet, valdSeplare).then((vydata) => {
            const authenticated = vydata['success'];

            if (authenticated === 'false') {
                setMessage('Du måste vara administratör för att använda denna sidan.');
            } else {
                setSpelare(vydata['data']['spelarInfo']);
                setTransaktioner(vydata['data']['transaktioner']);

                // reset values
                setSpelareBeskrivning('');
                setSpelareKredit(0);
                setSpelareDebet(0);
                setSpelareDatum(new Date().toISOString());
                setMessage(null);
            }
        }, (failed) => {
            setMessage('Network connection error when calling REST API, status code = ' + failed);
        });
        event.preventDefault();
    }
    const taBetaltAvAllaSpelare = function (event) {
        event.preventDefault();
        const bffService = new BFF();
        bffService.taBetaltAvAllaSpelare(namn).then((vydata) => {
            const authenticated = vydata['success'];

            if (authenticated === 'false') {
                setMessage('Du måste vara administratör för att använda denna funktion.');
            } else {
                setSaldo(vydata['data']['saldo']);
                setSpelare(vydata['data']['spelarInfo']);
                setMessage(null);
            }
        }, (failed) => {
            setMessage('Network connection error when calling REST API, status code = ' + failed);
        });

        event.preventDefault();
    }

    const visaTransaktioner = function (kontonummer, userid) {
        setValdSpelare(userid);
        const bffService = new BFF();
        bffService.transaktionerForKontonummer(kontonummer).then((vydata) => {
            const authenticated = vydata['success'];

            if (authenticated === 'false') {
                setMessage('Du måste vara administratör för att använda denna sidan.');
            } else {
                setTransaktioner(vydata['data']['transaktioner']);
                setMessage(null);
            }
        }, (failed) => {
            alert('Network connection error when calling REST API, status code = ' + failed);
        });
    }

    //
    // Presentationslogik
    //
    const error = function() {
        return (<div>
                <div className="message">{message}</div>
            </div>
        );
    };

    const inloggadContent = function () {
        return (<div>
            <div  className='formarea'>
                <b>Administrera spelbolag:</b> {namn}.<br/>
                <b>Insats per omgång:</b> {insatsperomgang} kr.<br/>
                <b>Saldo:</b> {saldo} kr.<br/>
                ----------------------------------------------------<br/>
                <b>Ny transaktion</b><br/>
                <form onSubmit={laggTillTransaktionForSpelbolag}>
                    <label>Beskrivning:<input type="text" value={beskrivning}
                                    name='beskrivning' maxLength='50' style = {{width: 400}}
                                    onChange={event => setBeskrivning(event.target.value)} />
                    </label><br/>
                    <label>Kredit (- på kontot):<input type="text" maxLength='5' value={kredit} style = {{width: 50}}
                                    name='kredit' onChange={event => setKredit(event.target.value)} />
                    </label><br/>
                    <label>Debet (+ på kontot):<input type="text" maxLength='5' value={debet} style = {{width: 50}}
                                    name='debet' onChange={event => setDebet(event.target.value)} /></label><br/>
                   <input type="submit" value="Lägg till transaktion" />
                </form>
                ----------------------------------------------------<br/>
                <b>Ta betalt av alla spelare</b><br/>
                <form onSubmit={taBetaltAvAllaSpelare}>
                    Det kommer att dras {insatsperomgang} kr ifrån varje spelare som är med i spelbolag {namn}.<br/>
                   <input type="submit" value="Ta betalt" />
                </form>
             </div>
             <br/><br/>
             <div  className='formarea'>
                 <b>Alla spelare som ingår i spelbolaget</b><br/>
                  <table>
                     <thead>
                         <tr>
                             <th>Användarid</th>
                             <th>Saldo</th>
                         </tr>
                     </thead>
                     <tbody>
                         {
                             spelare.map((currentValue) => {
                                 return (<tr key={currentValue['userid']} onClick={() => visaTransaktioner(currentValue['kontonummer'], currentValue['userid'])}>
                                    <td>{currentValue['userid']}</td>
                                    <td>{currentValue['saldo']}</td>
                                 </tr>
                                 );
                             })
                         }
                       </tbody>
                  </table>
                  ----------------------------------------------------<br/>
                  Administrera {valdSeplare}.<br/>
                  <b>Ny transaktion</b><br/>
                  <form onSubmit={laggTillTransaktionForSpelare}>
                      <label>Datum:<input type="text" name='spelaredatum'
                                value={spelareDatum} onChange={event => setSpelareDatum(event.target.value)} />
                      (ISO 8601 datumformat)</label><br/>
                      <label>Beskrivning:<input type="text" name='spelarebeskrivning' maxLength='50'
                                style = {{width: 400}} value={spelareBeskrivning}
                                onChange={event => setSpelareBeskrivning(event.target.value)} />
                      </label><br/>
                      <label>Kredit:<input type="text" maxLength='5' style = {{width: 50}}
                                 name='spelarekredit' value={spelareKredit}
                                onChange={event => setSpelareKredit(event.target.value)} />
                      </label><br/>
                      <label>Debet:<input type="text" maxLength='5' style = {{width: 50}}
                             name='spelaredebet' value={spelareDebet}
                            onChange={event => setSpelareDebet(event.target.value)} /></label><br/>
                     <input type="submit" value="Lägg till transaktion" />
                  </form>
                   <b>Transaktioner:</b>
                    <table>
                      <thead>
                          <tr>
                              <th>Datum</th>
                              <th>Beskrivning</th>
                              <th>Debet (+)</th>
                              <th>Kredit (-)</th>
                              <th>Saldo</th>
                          </tr>
                      </thead>
                      <tbody>
                          {
                              transaktioner.map((currentValue) => {
                                  return (<tr key={currentValue['ID']}>
                                    <td>{currentValue['datum']}</td>
                                    <td>{currentValue['beskrivning']}</td>
                                    <td>{currentValue['debet']}</td>
                                    <td>{currentValue['kredit']}</td>
                                    <td>{currentValue['saldo']}</td>
                                  </tr>
                                  );
                              })
                          }
                        </tbody>
                    </table>
              </div>
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