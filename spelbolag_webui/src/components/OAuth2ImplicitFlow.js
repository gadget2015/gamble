
/**
 * Hanterar inloggning och utloggning med OAuth2 implicit flow mot
 * Google inloggningslösning.
 */
class OAuth2ImplicitFlow {
    constructor(setInloggad, setUsername) {
        this.GoogleAuth = {};
        this.SCOPE = 'https://www.googleapis.com/auth/userinfo.email';
        this.setInloggad = setInloggad;
        this.setUsername = setUsername;
    }

    initClient(parent) {
        //console.log('initClient(): Callback från gapi.load = ' + this.SCOPE);

        // Initialize the gapi.client object, which app uses to make API requests.
        // Get API key and client ID from API Console.
        // 'scope' field specifies space-delimited list of access scopes.
        window.gapi.client.init({
            'apiKey': 'AIzaSyDzXLOCEQpzFnYxcMyvdtts3dMq5HbXM60',
            'clientId': '232397828773-bd9lq6qeukt33pb6f9aql166hlqpnfcj.apps.googleusercontent.com',
            'discoveryDocs': [],
            'scope': this.SCOPE
        }).then(function () {
            parent.GoogleAuth = window.gapi.auth2.getAuthInstance();

            // Listen for sign-in state changes.
            //parent.GoogleAuth.isSignedIn.listen(parent.updateSigninStatus);
            parent.GoogleAuth.isSignedIn.listen(parent.createSigninStatusCallbackFunction(parent));

            // Handle initial sign-in state. (Determine if user is already signed in.)
            parent.GoogleAuth.currentUser.get();
            parent.setSigninStatus();
        });
    }

    /**
     * Skapar en callbackfunktion som kan komma åt en skapad klassinstans, dvs. parent=this.
     * Detta gör det möjligt att komma åt this.SCOPE, vilket är definierad i konstruktorn.
     */
    createSigninStatusCallbackFunction(parent) {
         return function() {
            parent.updateSigninStatus(parent)
        }
    }

    mycall(parent) {
        parent.initClient(parent);
    }

    /**
     * Load the API's client and auth2 modules.
     * Call the initClient function after the modules load.
     */
    handleClientLoad() {
        // Skapar en callbackfunktion som kan komma åt denna klassinstans, dvs. parent = this.
        // Detta för att komma åt SCOPE som sitter på denna klassinstans i konstruktorn.
        const initCallback = function(parent) {
            return function z() {
                return parent.mycall(parent);
            }
        }

        let zz = initCallback(this);

        window.gapi.load('client:auth2', zz);
    }

    updateSigninStatus() {
        this.setSigninStatus();
    }

    /**
     * Uppdaterar om användaren är inloggad, och då med vilket email/username.
     */
    setSigninStatus() {
        var user = this.GoogleAuth.currentUser.get();
        var isAuthorized = user.hasGrantedScopes(this.SCOPE);

        if (isAuthorized) {
            var username = user.getBasicProfile().getEmail();
            console.log('Inloggad med email =' + username);
            //console.log('id_token' + user.getAuthResponse().id_token);
            this.setUsername(username);
            this.setInloggad(true);
        } else {
            this.setUsername('');
            this.setInloggad(false);
        }
    }

    login() {
        this.GoogleAuth.signIn();
    }

    logout() {
        this.GoogleAuth.signOut();
    }
};

export {OAuth2ImplicitFlow};