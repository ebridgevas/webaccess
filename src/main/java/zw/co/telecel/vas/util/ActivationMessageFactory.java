package zw.co.telecel.vas.util;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 1/21/14
 * Time: 6:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActivationMessageFactory {

    public String createPayloadOldVersion() {
        String payload = "";
        payload += "<div style=\"opacity: 0.6;\"></div>" +
                "<div style=\"display: block;\">" +
                "    <div" +
                "            style=\"width: 390px;" +
                "        max-width: 390px;" +
                "        display: block;" +
                "        margin-bottom: auto;" +
                "    margin-left: auto;" +
                "    margin-right: auto;" +
                "    margin-top: auto;" +
                "    position: absolute;font-family: 'Helvetica Neue', Arial, sans-serif;" +
                "    font-size: 14px;" +
                "    line-height: 16px;" +
                "    text-align: left;" +
                "    background: white;" +
                "    border: 1px solid #909399;" +
                "    -webkit-border-radius: 5px;" +
                "    -moz-border-radius: 5px;" +
                "    border-radius: 5px;" +
                "    -webkit-box-shadow: 0 6px 18px rgba(0, 0, 0, 0.25);" +
                "    -moz-box-shadow: 0 6px 18px rgba(0, 0, 0, 0.25);" +
                "    box-shadow: 0 6px 18px rgba(0, 0, 0, 0.25);" +
                "    margin: 70px auto 50px;" +
                "    width: 600px;" +
                "    z-index: 5000;" +
                "    background-clip: padding-box;" +
                "    color: #5E5E5E;" +
                "    font-size: 1.1em;" +
                "    position: relative\">" +
                "        <div style=\"min-height: 100px;\">" +
                "            <div style=\"background-color: #F2F4F7;" +
                "    filter: progid:DXImageTransform.Microsoft.gradient(StartColorStr=#fffafbfc, EndColorStr=#fff2f4f7, GradientType=0);" +
                "    -ms-filter: 'progid:DXImageTransform.Microsoft.gradient(StartColorStr=#fffafbfc, EndColorStr=#fff2f4f7, GradientType=0)';" +
                "    background-repeat: repeat-x;" +
                "    background-color: #f6f8fa;" +
                "    background-image: -webkit-linear-gradient(top, #fafbfc, #F2F4F7);" +
                "    background-image: -moz-linear-gradient(top, #fafbfc, #F2F4F7);" +
                "    background-image: -ms-linear-gradient(top, #fafbfc, #F2F4F7);" +
                "    background-image: -o-linear-gradient(top, #fafbfc, #F2F4F7);" +
                "    background-image: linear-gradient(top, #fafbfc, #F2F4F7);" +
                "    width: 100%;" +
                "    height: 71px;" +
                "    font-weight: 200;" +
                "    -webkit-border-radius: 5px 5px 0px 0px;" +
                "    -moz-border-radius: 5px 5px 0px 0px;" +
                "    border-radius: 5px 5px 0px 0px;" +
                "    -webkit-box-shadow: inset 0 -2px 3px rgba(0, 0, 0, 0.04);" +
                "    -moz-box-shadow: inset 0 -2px 3px rgba(0, 0, 0, 0.04)\">" +
                "    <span style=\"line-height: 72px;" +
                "    font-size: 26px;" +
                "    color: #066eac;" +
                "    float: left;" +
                "    font-weight: 200;" +
                "    margin: 0 27px\">Telecel Web Portal Activation</span></div>" +
                "            <div style=\"padding: 27px;" +
                "    background: white;" +
                "    *zoom: 1;" +
                "    -webkit-border-radius: 5px;" +
                "    -moz-border-radius: 5px;" +
                "    border-radius: 5px;    display: table;" +
                "    content: '';" +
                "            line-height: 0;clear: both\">" +
                "                <form action=\"/portal.html\" method=\"POST\"" +
                "                      novalidate=\"novalidate\" autocomplete=\"off\" style=\"float: left;padding: 0 27px;margin-left: -27px\">" +
                "                    <div style=\"margin-bottom: 30px;font-weight: 300;color: #000;" +
                "    font-family: Helvetica, Arial, Verdana, sans-serif;" +
                "    font-size: 12px;\"> Hi David," +
                "                    </div>" +
                "                    <div style=\"margin-bottom: 15px;font-weight: 300;color: #000;" +
                "    font-family: Helvetica, Arial, Verdana, sans-serif;" +
                "    font-size: 12px;\"> if your Internet Service Provider is Telecel click:" +
                "                    </div>" +
                "                    <button id=\"activateISPTelecel\" style=\"cursor: pointer;color: #fff !important; width: 94%;" +
                "    padding: 14px 10px;" +
                "    margin-bottom: 30px;" +
                "    font-size: 17px;" +
                "    -webkit-box-sizing: content-box;" +
                "    -moz-box-sizing: content-box;" +
                "    box-sizing: content-box; -webkit-box-shadow: inset 0 1px 0 #1f9eee;" +
                "    -moz-box-shadow: inset 0 1px 0 #1f9eee;" +
                "    box-shadow: inset 0 1px 0 #1f9eee;" +
                "    -webkit-box-shadow: inset 0 1px 0 #1f9eee;" +
                "    -moz-box-shadow: inset 0 1px 0 #1f9eee;" +
                "    box-shadow: inset 0 1px 0 #1f9eee;" +
                "    filter: progid:DXImageTransform.Microsoft.gradient(StartColorStr=#ff187dbd, EndColorStr=#ff0065a5, GradientType=0);" +
                "    -ms-filter: 'progid:DXImageTransform.Microsoft.gradient(StartColorStr=#ff187dbd, EndColorStr=#ff0065a5, GradientType=0)';" +
                "    background-repeat: repeat-x;" +
                "    background-color: #0c71b1;" +
                "    background-image: -webkit-linear-gradient(top, #187dbd, #0065a5);" +
                "    background-image: -moz-linear-gradient(top, #187dbd, #0065a5);" +
                "    background-image: -ms-linear-gradient(top, #187dbd, #0065a5);" +
                "    background-image: -o-linear-gradient(top, #187dbd, #0065a5);" +
                "    background-image: linear-gradient(top, #187dbd, #0065a5);" +
                "    filter: progid:DXImageTransform.Microsoft.gradient(StartColorStr=#ff187dbd, EndColorStr=#ff0065a5, GradientType=0);" +
                "    -ms-filter: 'progid:DXImageTransform.Microsoft.gradient(StartColorStr=#ff187dbd, EndColorStr=#ff0065a5, GradientType=0)';" +
                "    background-repeat: repeat-x;" +
                "    background-color: #0c71b1;" +
                "    background-image: -webkit-linear-gradient(top, #187dbd, #0065a5);" +
                "    background-image: -moz-linear-gradient(top, #187dbd, #0065a5);" +
                "    background-image: -ms-linear-gradient(top, #187dbd, #0065a5);" +
                "    background-image: -o-linear-gradient(top, #187dbd, #0065a5);" +
                "    background-image: linear-gradient(top, #187dbd, #0065a5);" +
                "    color: #fff;" +
                "    text-shadow: 0 -1px 0 #004068;" +
                "    border: 1px solid #00558c\">" +
                "        Activate from Telecel" +
                "    </button>" +
                "    <div style=\"margin-bottom: 15px;font-weight: 300; color: #000;" +
                "    font-family: Helvetica, Arial, Verdana, sans-serif;" +
                "    font-size: 12px;\"> if your Internet Service Provider is NOT Telecel click:" +
                "                    </div>" +
                "    <button id=\"activateISPNotTelecel\" style=\"cursor: pointer; width: 94%;" +
                "    padding: 14px 10px;" +
                "    margin-bottom: 30px;" +
                "    font-size: 17px;" +
                "    -webkit-box-sizing: content-box;" +
                "    -moz-box-sizing: content-box;" +
                "    box-sizing: content-box; -webkit-box-shadow: inset 0 1px 0 #1f9eee;" +
                "    -moz-box-shadow: inset 0 1px 0 #1f9eee;" +
                "    box-shadow: inset 0 1px 0 #1f9eee;" +
                "    -webkit-box-shadow: inset 0 1px 0 #1f9eee;" +
                "    -moz-box-shadow: inset 0 1px 0 #1f9eee;" +
                "    box-shadow: inset 0 1px 0 #1f9eee;" +
                "    filter: progid:DXImageTransform.Microsoft.gradient(StartColorStr=#ff187dbd, EndColorStr=#ff0065a5, GradientType=0);" +
                "    -ms-filter: 'progid:DXImageTransform.Microsoft.gradient(StartColorStr=#ff187dbd, EndColorStr=#ff0065a5, GradientType=0)';" +
                "    background-repeat: repeat-x;" +
                "    background-color: #0c71b1;" +
                "    background-image: -webkit-linear-gradient(top, #187dbd, #0065a5);" +
                "    background-image: -moz-linear-gradient(top, #187dbd, #0065a5);" +
                "    background-image: -ms-linear-gradient(top, #187dbd, #0065a5);" +
                "    background-image: -o-linear-gradient(top, #187dbd, #0065a5);" +
                "    background-image: linear-gradient(top, #187dbd, #0065a5);" +
                "    filter: progid:DXImageTransform.Microsoft.gradient(StartColorStr=#ff187dbd, EndColorStr=#ff0065a5, GradientType=0);" +
                "    -ms-filter: 'progid:DXImageTransform.Microsoft.gradient(StartColorStr=#ff187dbd, EndColorStr=#ff0065a5, GradientType=0)';" +
                "    background-repeat: repeat-x;" +
                "    background-color: #0c71b1;" +
                "    background-image: -webkit-linear-gradient(top, #187dbd, #0065a5);" +
                "    background-image: -moz-linear-gradient(top, #187dbd, #0065a5);" +
                "    background-image: -ms-linear-gradient(top, #187dbd, #0065a5);" +
                "    background-image: -o-linear-gradient(top, #187dbd, #0065a5);" +
                "    background-image: linear-gradient(top, #187dbd, #0065a5);" +
                "    color: #fff;" +
                "    text-shadow: 0 -1px 0 #004068;" +
                "    border: 1px solid #00558c\">" +
                "        Activate from Other ISPs" +
                "    </button>" +
                "" +
                "                </form>" +
                "            </div>" +
                "        </div>" +
                "    </div>" +
                "</div>";
        return payload;
    }

    public String createPayload() {
        String payload = "";
        payload += "    <table\n" +
                "            style=\"width: 390px; \n" +
                "        max-width: 390px; \n" +
                "        display: block; \n" +
                "        margin-bottom: auto; \n" +
                "    margin-left: auto; \n" +
                "    margin-right: auto; \n" +
                "    margin-top: auto; \n" +
                "    position: absolute;font-family: 'Helvetica Neue', Arial, sans-serif; \n" +
                "    font-size: 14px; \n" +
                "    line-height: 16px; \n" +
                "    text-align: left; \n" +
                "    background: white; \n" +
                "    border: 1px solid #909399; \n" +
                "    -webkit-border-radius: 5px; \n" +
                "    -moz-border-radius: 5px; \n" +
                "    border-radius: 5px; \n" +
                "    -webkit-box-shadow: 0 6px 18px rgba(0, 0, 0, 0.25); \n" +
                "    -moz-box-shadow: 0 6px 18px rgba(0, 0, 0, 0.25); \n" +
                "    box-shadow: 0 6px 18px rgba(0, 0, 0, 0.25); \n" +
                "    margin: 70px auto 10px;\n" +
                "    width: 600px; \n" +
                "    z-index: 5000; \n" +
                "    background-clip: padding-box; \n" +
                "    color: #5E5E5E; \n" +
                "    font-size: 1.1em; \n" +
                "    position: relative\">\n" +
                "\n" +
                "        <tr style=\"min-height: 100px;\">\n" +
                "            <td style=\"background-color: #F2F4F7;\n" +
                "    filter: progid:DXImageTransform.Microsoft.gradient(StartColorStr=#fffafbfc, EndColorStr=#fff2f4f7, GradientType=0); \n" +
                "    -ms-filter: 'progid:DXImageTransform.Microsoft.gradient(StartColorStr=#fffafbfc, EndColorStr=#fff2f4f7, GradientType=0)'; \n" +
                "    background-repeat: repeat-x; \n" +
                "    background-color: #f6f8fa; \n" +
                "    background-image: -webkit-linear-gradient(top, #fafbfc, #F2F4F7); \n" +
                "    background-image: -moz-linear-gradient(top, #fafbfc, #F2F4F7); \n" +
                "    background-image: -ms-linear-gradient(top, #fafbfc, #F2F4F7); \n" +
                "    background-image: -o-linear-gradient(top, #fafbfc, #F2F4F7); \n" +
                "    background-image: linear-gradient(top, #fafbfc, #F2F4F7); \n" +
                "    width: 100%; \n" +
                "    height: 71px; \n" +
                "    font-weight: 200; \n" +
                "    -webkit-border-radius: 5px 5px 0px 0px; \n" +
                "    -moz-border-radius: 5px 5px 0px 0px; \n" +
                "    border-radius: 5px 5px 0px 0px; \n" +
                "    -webkit-box-shadow: inset 0 -2px 3px rgba(0, 0, 0, 0.04); \n" +
                "    -moz-box-shadow: inset 0 -2px 3px rgba(0, 0, 0, 0.04)\"> \n" +
                "    <span style=\"line-height: 72px; \n" +
                "    font-size: 26px; \n" +
                "    color: #066eac; \n" +
                "    float: left; \n" +
                "    font-weight: 200; \n" +
                "    margin: 0 27px\">Telecel Web Portal Activation</span></td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td>\n" +
                "                <form action=\"/portal.html\" method=\"POST\" \n" +
                "                      novalidate=\"novalidate\" autocomplete=\"off\" style=\"float: left;padding: 0 27px;margin-left: -27px\">\n" +
                "\n" +
                "\n" +
                "                        <tr style=\"min-height: 100px;\">\n" +
                "                    <td style=\"margin-bottom: 30px;font-weight: 300;color: #000;\n" +
                "    font-family: Helvetica, Arial, Verdana, sans-serif; \n" +
                "    font-size: 12px;padding-left: 30px;padding-bottom: 30px;\"> Hi David,\n" +
                "                    </td>\n" +
                "                    </tr>\n" +
                "                        <tr style=\"min-height: 100px;\">\n" +
                "                    <td style=\"margin-bottom: 15px;font-weight: 300;color: #000;\n" +
                "    font-family: Helvetica, Arial, Verdana, sans-serif; \n" +
                "    font-size: 12px;padding-left: 30px;padding-bottom: 10px;\"> if your Internet Service Provider is Telecel click:\n" +
                "                    </td>\n" +
                "                    </tr>\n" +
                "                    <tr style=\"min-height: 100px;\">\n" +
                "                        <td>\n" +
                "                    <button id=\"activateISPTelecel\" style=\"cursor: pointer;margin-left: 60px;color: #fff !important; width: 64%;\n" +
                "    padding: 14px 10px; \n" +
                "    margin-bottom: 30px; \n" +
                "    font-size: 17px; \n" +
                "    -webkit-box-sizing: content-box; \n" +
                "    -moz-box-sizing: content-box; \n" +
                "    box-sizing: content-box; -webkit-box-shadow: inset 0 1px 0 #1f9eee; \n" +
                "    -moz-box-shadow: inset 0 1px 0 #1f9eee; \n" +
                "    box-shadow: inset 0 1px 0 #1f9eee; \n" +
                "    -webkit-box-shadow: inset 0 1px 0 #1f9eee; \n" +
                "    -moz-box-shadow: inset 0 1px 0 #1f9eee; \n" +
                "    box-shadow: inset 0 1px 0 #1f9eee; \n" +
                "    filter: progid:DXImageTransform.Microsoft.gradient(StartColorStr=#ff187dbd, EndColorStr=#ff0065a5, GradientType=0); \n" +
                "    -ms-filter: 'progid:DXImageTransform.Microsoft.gradient(StartColorStr=#ff187dbd, EndColorStr=#ff0065a5, GradientType=0)'; \n" +
                "    background-repeat: repeat-x; \n" +
                "    background-color: #0c71b1; \n" +
                "    background-image: -webkit-linear-gradient(top, #187dbd, #0065a5); \n" +
                "    background-image: -moz-linear-gradient(top, #187dbd, #0065a5); \n" +
                "    background-image: -ms-linear-gradient(top, #187dbd, #0065a5); \n" +
                "    background-image: -o-linear-gradient(top, #187dbd, #0065a5); \n" +
                "    background-image: linear-gradient(top, #187dbd, #0065a5); \n" +
                "    filter: progid:DXImageTransform.Microsoft.gradient(StartColorStr=#ff187dbd, EndColorStr=#ff0065a5, GradientType=0); \n" +
                "    -ms-filter: 'progid:DXImageTransform.Microsoft.gradient(StartColorStr=#ff187dbd, EndColorStr=#ff0065a5, GradientType=0)'; \n" +
                "    background-repeat: repeat-x; \n" +
                "    background-color: #0c71b1; \n" +
                "    background-image: -webkit-linear-gradient(top, #187dbd, #0065a5); \n" +
                "    background-image: -moz-linear-gradient(top, #187dbd, #0065a5); \n" +
                "    background-image: -ms-linear-gradient(top, #187dbd, #0065a5); \n" +
                "    background-image: -o-linear-gradient(top, #187dbd, #0065a5); \n" +
                "    background-image: linear-gradient(top, #187dbd, #0065a5); \n" +
                "    color: #fff; \n" +
                "    text-shadow: 0 -1px 0 #004068; \n" +
                "    border: 1px solid #00558c\"> \n" +
                "        Activate from Telecel \n" +
                "    </button>\n" +
                "     </td>\n" +
                "    </tr>\n" +
                "\n" +
                "                    <tr style=\"min-height: 100px;\">\n" +
                "\n" +
                "    <td style=\"margin-bottom: 15px;font-weight: 300; color: #000;\n" +
                "    font-family: Helvetica, Arial, Verdana, sans-serif; \n" +
                "    font-size: 12px;padding-left: 30px;padding-bottom: 10px;\"> if your Internet Service Provider is NOT Telecel click:\n" +
                "                    </td>\n" +
                "    </tr>\n" +
                "                    <tr style=\"min-height: 100px;\">\n" +
                "                        <td>\n" +
                "    <button id=\"activateISPNotTelecel\" style=\"cursor: pointer; margin-left: 60px; width: 64%;\n" +
                "    padding: 14px 10px; \n" +
                "    margin-bottom: 30px; \n" +
                "    font-size: 17px; \n" +
                "    -webkit-box-sizing: content-box; \n" +
                "    -moz-box-sizing: content-box; \n" +
                "    box-sizing: content-box; -webkit-box-shadow: inset 0 1px 0 #1f9eee; \n" +
                "    -moz-box-shadow: inset 0 1px 0 #1f9eee; \n" +
                "    box-shadow: inset 0 1px 0 #1f9eee; \n" +
                "    -webkit-box-shadow: inset 0 1px 0 #1f9eee; \n" +
                "    -moz-box-shadow: inset 0 1px 0 #1f9eee; \n" +
                "    box-shadow: inset 0 1px 0 #1f9eee; \n" +
                "    filter: progid:DXImageTransform.Microsoft.gradient(StartColorStr=#ff187dbd, EndColorStr=#ff0065a5, GradientType=0); \n" +
                "    -ms-filter: 'progid:DXImageTransform.Microsoft.gradient(StartColorStr=#ff187dbd, EndColorStr=#ff0065a5, GradientType=0)'; \n" +
                "    background-repeat: repeat-x; \n" +
                "    background-color: #0c71b1; \n" +
                "    background-image: -webkit-linear-gradient(top, #187dbd, #0065a5); \n" +
                "    background-image: -moz-linear-gradient(top, #187dbd, #0065a5); \n" +
                "    background-image: -ms-linear-gradient(top, #187dbd, #0065a5); \n" +
                "    background-image: -o-linear-gradient(top, #187dbd, #0065a5); \n" +
                "    background-image: linear-gradient(top, #187dbd, #0065a5); \n" +
                "    filter: progid:DXImageTransform.Microsoft.gradient(StartColorStr=#ff187dbd, EndColorStr=#ff0065a5, GradientType=0); \n" +
                "    -ms-filter: 'progid:DXImageTransform.Microsoft.gradient(StartColorStr=#ff187dbd, EndColorStr=#ff0065a5, GradientType=0)'; \n" +
                "    background-repeat: repeat-x; \n" +
                "    background-color: #0c71b1; \n" +
                "    background-image: -webkit-linear-gradient(top, #187dbd, #0065a5); \n" +
                "    background-image: -moz-linear-gradient(top, #187dbd, #0065a5); \n" +
                "    background-image: -ms-linear-gradient(top, #187dbd, #0065a5); \n" +
                "    background-image: -o-linear-gradient(top, #187dbd, #0065a5); \n" +
                "    background-image: linear-gradient(top, #187dbd, #0065a5); \n" +
                "    color: #fff; \n" +
                "    text-shadow: 0 -1px 0 #004068; \n" +
                "    border: 1px solid #00558c\"> \n" +
                "        Activate from Other ISPs \n" +
                "    </button> \n" +
                "         </td>\n" +
                "    </tr>\n" +
                "                </form> \n" +
                "            </td>\n" +
                "\n" +
                "        </tr>\n" +
                "    </table>";
        return payload;
    }
}
