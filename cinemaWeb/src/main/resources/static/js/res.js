
document.addEventListener("DOMContentLoaded", function(event) {

    var name = location.search.split('name=')[1];
    var i = 0;
    for(; i < name.length; i++){
        if(name.charAt(i)=='&') break;
    }
    name = decodeURI(name.substring(0,i));


});