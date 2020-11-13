import React from 'react';
import {useState} from 'react';
import {useEffect} from 'react';
import './Administration.css';
import {BFF} from './../service/BFF';

function Administration() {
    const [message, setMessage] = useState('');
    const [rerun] = useState(false);  // Ska bara hämta data en gång.

    // Laddar in vy data.
    useEffect( () => {
        const bffService = new BFF();

        bffService.administrationStartSida().then((vydata) => {
            const authenticated = vydata['success'];

            if (authenticated === false) {
                setMessage('Du måste logga in för att kunna se ditt saldo.');
            } else {

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

    // Visar olika UI delar beroende på om man är inloggad eller inte = conditional rendering.
    let uiContent;

    if (message !== null) {
        uiContent = error();
    } else {
        console.log('Visa innehållet.');
    }

    return (
        <div>
            Administration
             {uiContent}
        </div>
    );
}

export { Administration };