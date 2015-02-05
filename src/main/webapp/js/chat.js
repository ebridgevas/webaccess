/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 3/5/14
 * Time: 5:34 PM
 * To change this template use File | Settings | File Templates.
 */

function addMessage(message, isRecieved) {

    $('#chat-panel').append(
       " <div class=\"callout border-callout standard-font-black\" " +
       "     style=\"" +
           ( isRecieved ? " margin-left: 0px; " : " margin-right: 0px; ") +
       " margin-top: 10px; " +
       " padding-right: 10px; display:inline-block; " +
           ( isRecieved ? " float: left " : " float: right ") +
//           ( isRecieved ? " left: 0 " : " right: 0 ") +
       "        \">" +
            message +
       "   <b class=\"border-notch " +
           ( isRecieved ?  " notch " :  " notch-right "  ) +
       "    \"></b> " +
           ( isRecieved ? "   <b class=\"notch\"></b> " : "   <b class=\"notch-right\"></b> " ) +
       " </div> " +
       " <div class=\"clear\"></div>");

}
