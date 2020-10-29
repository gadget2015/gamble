
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
        console.log('OAuth2ImplicitFlow.constructor');
    }

    initClient(parent) {
        console.log('callback från gapi.load = ' + this.SCOPE);

        // Initialize the gapi.client object, which app uses to make API requests.
        // Get API key and client ID from API Console.
        // 'scope' field specifies space-delimited list of access scopes.
        window.gapi.client.init({
            'apiKey': 'AIzaSyDzXLOCEQpzFnYxcMyvdtts3dMq5HbXM60',
            'clientId': '232397828773-bd9lq6qeukt33pb6f9aql166hlqpnfcj.apps.googleusercontent.com',
            'discoveryDocs': [],
            'scope': this.SCOPE
        }).then(function () {
            console.log('callback from client.init.');
            parent.GoogleAuth = window.gapi.auth2.getAuthInstance();

            // Listen for sign-in state changes.
            //parent.GoogleAuth.isSignedIn.listen(parent.updateSigninStatus);
            parent.GoogleAuth.isSignedIn.listen(parent.createSigninStatusCallbackFunction(parent));

            // Handle initial sign-in state. (Determine if user is already signed in.)
            parent.GoogleAuth.currentUser.get();
            parent.setSigninStatus();
        });
    }

    createSigninStatusCallbackFunction(parent) {
        console.log('createSigninStatusCallbackFunction');
        return function() {
            console.log('callback from client.init.');
            parent.updateSigninStatus(parent)
        }
    }

    mycall(parent) {
        parent.initClient(parent);
    }

    handleClientLoad() {
        console.log('handleClientload');
        const initCallback = function(parent) {
            console.log('create function.');

            return function z() {
                console.log('z and this = ' + parent);
                return parent.mycall(parent);
            }
        }

        let zz = initCallback(this);

        // Load the API's client and auth2 modules.
        // Call the initClient function after the modules load.
        window.gapi.load('client:auth2', zz);
        console.log('handleClientload.end');
    }

    updateSigninStatus() {
        console.log('updateSigninStatus');
        this.setSigninStatus();
    }

    setSigninStatus() {
        var user = this.GoogleAuth.currentUser.get();

        var isAuthorized = user.hasGrantedScopes(this.SCOPE);
        if (isAuthorized) {
            console.log('Inloggad.');
            var username = user.getBasicProfile().getEmail();
            console.log('email=' + username);
            this.setUsername(username);
            this.setInloggad(true);
        } else {
            console.log('oinloggad');
            this.setUsername('');
            this.setInloggad(false);
        }
    }

    login() {
        console.log('Login user...');
        this.GoogleAuth.signIn();
    }

    logout() {
        console.log('Logout user...');
        this.GoogleAuth.signOut();
    }
};

export {OAuth2ImplicitFlow};