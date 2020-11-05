import React from 'react';
import {useEffect} from 'react';
import {useState} from 'react';
import {BFF} from './../service/BFF';

function Tipsbolag() {
    const BFFService = new BFF();
    const [rerun, setRerun] = useState(false);  // Ska bara hämta data en gång.
    const [spelbolag, setSpelbolag] = useState([]);

    // Laddar in vy data.
    useEffect( () => {
        BFFService.spelbolagStartSida().then((vydata) => {
            setSpelbolag(vydata['data']);

        }, (failed) => {
            alert('Network connection error when calling REST API, status code = ' + failed);
        });
    }, [rerun]);


    return (
        <div>
         <ul>
            {
                spelbolag.map((currentValue, index) => {
                        return <li key={index}>{currentValue['namn']}: insats {currentValue['insatsperomgang']} kr per omgång.</li>;
                        })
            }
         </ul>
        </div>
      );
}

export {Tipsbolag};