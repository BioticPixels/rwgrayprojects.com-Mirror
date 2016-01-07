 
isNS4 = (document.layers) ? true : false;
isIE4 = (document.all && !document.getElementById) ? true : false;
isIE5 = (document.all && document.getElementById) ? true : false;
isNS6 = (!document.all && document.getElementById) ? true : false;


if (isIE4 || isIE5)
{
  window.onscroll = moveMenu; 
  window.onresize = moveMenu;
} 
else
if (isNS4)
{
  
} // end if statement
else
if (isNS6)
{
  window.addEventListener("resize", moveMenu, false);

  document.addEventListener("keyup", moveMenu, true);
  document.addEventListener("mouseup", moveMenu, true);     

} 


function moveMenu()
{

   if (isIE5 || isIE4)
   {
     // determine the width of the browser window
     ww = document.body.offsetWidth;
     if (ww < 500) ww = 500;

     document.all["menu"].style.width = ww - 20;
  
     y = document.body.scrollTop;

     document.all["menu"].style.pixelTop = y;

   } // end if (isIE) statement

   
   if (isNS4)
   { 
     // determine the width of the browser window
     ww = window.innerWidth;
     if (ww < 500) ww = 500;

     document.layers["menu"].width = ww - 20;
   
     y = window.pageYOffset;


     document.layers["menu"].top = y;

   } // end if statement
 

   if (isNS6)
   {
     // determine the width of the browser window
     ww = window.innerWidth;
     if (ww < 500) ww = 500;

     document.getElementById("menu").style.width = ww - 20;
  
     y = window.pageYOffset;

     document.getElementById("menu").style.top = y;

   } // end if (isNS6) statement


} // end moveMenu


///////////////////////////////
// Print functions

function printthis()
{

  loc = ""+window.location;

  i = loc.lastIndexOf("/", loc.length); 
  j = loc.indexOf(".html", 0);

  loc = loc.substr(0, i) + "/print" + loc.substring(i, j)+".pdf";
 
  window.open(loc, "_blank");

//  alert("print function: "+loc);

  
} // end printthis

