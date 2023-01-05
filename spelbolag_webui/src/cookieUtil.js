/**
 * Set Cookie, function kopierad ifrån https://www.w3schools.com/js/js_cookies.asp.
 * The parameters of the function above are the name of the cookie (cname), the value of the cookie (cvalue),
 * and the number of days until the cookie should expire (exdays).
 * The function sets a cookie by adding together the cookiename, the cookie value, and the expires string.
 */
function setCookie(cname, cvalue, exdays) {
    let d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    let expires = "expires="+ d.toUTCString();
    let theCookie = cname + "=" + cvalue + ";" + expires + ";path=/";

    document.cookie = theCookie;
}

function getCookie (cname) {
   var name = cname + "=";
   var decodedCookie = decodeURIComponent(document.cookie);
   var ca = decodedCookie.split(';');
   for(var i = 0; i <ca.length; i++) {
     var c = ca[i];
     while (c.charAt(0) === ' ') {
       c = c.substring(1);
     }
     if (c.indexOf(name) === 0) {
       return c.substring(name.length, c.length);
     }
   }
   return "";
 }

function deleteCookie(cname) {
    document.cookie = cname + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT"
}
export { setCookie, getCookie, deleteCookie };