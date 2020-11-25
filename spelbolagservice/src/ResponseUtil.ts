/**
* Utility metod för att paketera ett OK svar.
*/
function paketeraSuccessResponse(response) {
    return function(result) {
        const data = result['bffResult'];

        response.status(200).send({
                        success: 'true',
                        message: 'Hämtat data.',
                        data: data
                    });
    }
}

/**
* Utility metod för att paketera ett Fail/rejection svar.
*/
function paketeraFailResponse(response) {
    return function(result) {
        response.status(200).send({
                        success: 'true',
                        message: result
                    });
    }
}

export {paketeraSuccessResponse, paketeraFailResponse};