var nAgt = navigator.userAgent;
var browserName = navigator.appName;
var nameOffset, verOffset, ix;

if ((verOffset = nAgt.indexOf("Opera")) != -1) {
    browserName = "Opera";
} else if ((verOffset = nAgt.indexOf("MSIE")) != -1) {
    browserName = "Microsoft Internet Explorer";
} else if ((verOffset = nAgt.indexOf("Chrome")) != -1) {
    browserName = "Chrome";
} else if ((verOffset = nAgt.indexOf("Safari")) != -1) {
    browserName = "Safari";
} else if ((verOffset = nAgt.indexOf("Firefox")) != -1) {
    browserName = "Firefox";
} else if ((nameOffset = nAgt.lastIndexOf(' ') + 1) < (verOffset = nAgt.lastIndexOf('/'))) {
    browserName = nAgt.substring(nameOffset, verOffset);
    if (browserName.toLowerCase() == browserName.toUpperCase()) {
        browserName = navigator.appName;
    }
}
document.write("<a href='javascript:void(0);' onclick='setHomePage(browserName)' title='Click to know how to set Goglogo as your home page in "+browserName+"' style='color:#ffea05;font-weight:bold;'>Set As Home Page</a>")