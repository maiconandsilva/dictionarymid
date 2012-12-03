/* ////////////////////////////////////////////////////////////////
*   
*   In the Name of Allah
*   
*   DICTIONARYFORMIDS-CREATOR
*   
*   This file is part of DictionaryForMIDs-Creator
*   Copyright (C) 2012 Karim Mahamane Karimou
*   DictionaryForMIDs-Creator is a GUI wrapper around various
*   DictionaryForMIDs tools, among others we have
*   DictdToDictionaryForMIDs, DictionaryGeneration,
*   JarCreator and BitmapFontGenerator.
*   
*   DictionaryForMIDs-Creator is free software;
*   you can redistribute it and/or modify it under the terms
*   of the GNU General Public License as published by the
*   Free Software Foundation; either version 2 of the License, or
*   (at your option) any later version.
*   
*   DictionaryForMIDs-Creator is distributed in the hope that
*   it will be useful, but WITHOUT ANY WARRANTY; without even
*   the implied warranty of MERCHANTABILITY or
*   FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*   	
*   You should have received a copy of the GNU General Public
*   License along with DictionaryForMIDs-Creator;
*   if not, write to the Free Software Foundation, Inc.,
*   51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
*   
* //////////////////////////////////////////////////////////////// */


package de.kugihan.DfMCreator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexpUtils {
    
    /*
     * I took all the following regular
     * expression patterns on the internet.
     */
    
    // valid email pattern variable
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    ////////////////////////////////////////////////////////////////////////////
    // valid URL pattern variables
    // This regular expression tests the validity of a domain or hostname.
    // It will match any valid domain name that does not contain characters
    // which are invalid in URLs, and which ends in .com, .org, .net, .mil,
    // or .edu. You can add additional valid TLDs by appending the | (pipe)
    // character and the desired TLD to the list in the parens.
    //
    // Matches: 3SquareBand.com | asp.net | army.mil
    // Non-Matches: $SquareBand.com | asp/dot.net | army.military */
    private static final String URL_PATTERN1 = "^[a-zA-Z0-9\\-\\.]+\\.(com|org|net|mil|edu|fr|ne|COM|ORG|NET|MIL|EDU|FR|NE)$";
    
    
    //  Regular Expression matches any internet URLs.
    //  Used with the replace method it comes in very handy.
    //  
    //  Matches: http://www.aspemporium.com
    //           mailto:dominionx@hotmail.com
    //           ftp://ftp.test.com
    //          
    //  Non-Matches: www.aspemporium.com
    //               dominionx@hotmail.com
    private static final String URL_PATTERN2 = "((mailto\\:|(news|(ht|f)tp(s?))\\://){1}\\S+)";
    
    
    //  Verifies URLs. Checks for the leading protocol, a good looking domain
    //  (two or three letter TLD; no invalid characters in domain)
    //  and a somwhat reasonable file path.
    //  
    //  Matches: http://psychopop.org
    //           http://www.edsroom.com/newUser.asp
    //           http://unpleasant.jarrin.net/markov/inde
    //           
    //  Non-Matches: ftp://psychopop.org
    //               http://www.edsroom/
    //               http://un/pleasant.jarrin.net/markov/index.asp
    //  
    private static final String URL_PATTERN3 = "^http\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?$";
    
    
    //  *CORRECTED: Again thanks for all the comments below. If you want to include
    //  internal domain as well change the partial code (\.[\w-_]+)+ to (\.[\w-_]+)?
    //  See the comments below* This is the regular expression I use to add links in
    //  my email program. It also ignores those suppose-to-be commas/periods/colons
    //  at the end of the URL, like this sentence "check out
    //  http://www.yahoo.com/." (the period will be ignored) Note that it
    //  requires some modification to match ones that dont start with http.
    //  
    //  Matches: http://regxlib.com/Default.aspx
    //           http://electronics.cnet.com/electronics/0-6342366-8-8994967-1.html
    //           
    //  Non-Matches: www.yahoo.com
    private static final String URL_PATTERN4 = "(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";
    
    
    //  Modified URL RegExp that requires (http, https, ftp):// A nice
    //  domain, and a decent file/folder string. Allows : after
    //  domain name, and these characters in the file/folder sring
    //  (letter, numbers, - . _ ? , ' / \ + &amp; % $ # = ~).
    //  Blocks all other special characters-good for protecting
    //  against user input!
    //  
    //  Matches: http://www.blah.com/~joe
    //           ftp://ftp.blah.co.uk:2828/blah%20blah.gif
    //           https://blah.gov/blah-blah.as
    //           
    //  Non-Matches: www.blah.com
    //               http://www.blah&quot;blah.com/I have spaces!
    //               ftp://blah_underscore/[nope]
    private static final String URL_PATTERN5 = "^(http|https|ftp)\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(:[a-zA-Z0-9]*)?/?([a-zA-Z0-9\\-\\._\\?\\,\\'/\\\\\\+&amp;%\\$#\\=~])*$";
    
    
    //  This Regex (can be used e.g. in PHP with eregi) will match any valid URL.
    //  Unlike the other exapmles here, it will NOT match a valid URL ending
    //  with a dot or bracket. This is important if you use this regex to
    //  find and &quot;activate&quot; Links in an Text
    //  
    //  Matches: https://www.restrictd.com/~myhome/
    //  
    //  Non-Matches: http://www.krumedia.com.
    //               (http://www.krumedia.com)
    //               http://www.krumedia.com,    
    private static final String URL_PATTERN6 = "^(http|https|ftp)\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(:[a-zA-Z0-9]*)?/?([a-zA-Z0-9\\-\\._\\?\\,\\'/\\\\\\+&amp;%\\$#\\=~])*[^\\.\\,\\)\\(\\s]$";
    

    //  None of the other URL regex's seemed to work right for me,
    //  so i threw this together. works well with PHP's ereg().
    //  
    //  Matches: http://www.sysrage.net
    //           https://64.81.85.161/site/file.php?cow=moo's
    //           ftp://user:pass@host.com:123
    //           
    //  Non-Matches: sysrage.net    
    private static final String URL_PATTERN7 = "^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-Z0-9\\.&amp;%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&amp;%\\$#\\=~_\\-@]*)*$";
    

    //  Ok here's an updated URL regex for you folks. It allows
    //  localhost and all TLDs. Feel free to add each country
    //  code individually if you want a tighter match.
    //  
    //  Matches: http://site.com/dir/file.php?var=moo
    //           https://localhost
    //           ftp://user:pass@site.com:21/file/dir
    //           
    //  Non-Matches: site.com
    //               http://site.com/dir//    
    private static final String URL_PATTERN8 = "^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-Z0-9\\.&amp;%\\$\\-]+)*@)*((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|localhost|([a-zA-Z0-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(\\:[0-9]+)*(/($|[a-zA-Z0-9\\.\\,\\?\\'\\\\\\+&amp;%\\$#\\=~_\\-]+))*$";
    

    //  Version 1.3.0: I needed a regexp to validate URL's without
    //  the ht(f)tp(s):// and include North American domains
    //  (like .us and .ca) and there didn't seem to be one
    //  available...so I created one. It will also work with
    //  ASP QueryStrings and anchor URL's. If you have a problem
    //  with the expression or have any suggestions to improve,
    //  please write me and let me know. Added .uk domain and
    //  expression now allows for URLs that contain JSP session
    //  IDs. 4/14/04 - added ability to include URLs that start
    //  with server names.
    //  
    //  Matches: www.blah.com:8103
    //           www.blah.com/blah.asp?sort=ASC
    //           www.blah.com/blah.htm#blah
    //           
    //  Non-Matches: www.state.ga
    //               http://www.blah.ru    
    private static final String URL_PATTERN9 = "^(((ht|f)tp(s?))\\://)?(www.|[a-zA-Z].)[a-zA-Z0-9\\-\\.]+\\.(com|edu|gov|mil|net|org|biz|info|name|museum|us|ca|uk)(\\:[0-9]+)*(/($|[a-zA-Z0-9\\.\\,\\;\\?\\'\\\\\\+&amp;%\\$#\\=~_\\-]+))*$";
    

    //  Will locate an URL in a webpage. It'll search in 2 ways - first
    //  it will try to locate a href=, and then go to the end of the link.
    //  If there is nu href=, it will search for the end of the file
    //  instead (.asp, .htm and so on), and then take the data
    //  between the &quot;xxxxxx&quot; or 'xxxxxx'
    //  
    //  Matches: href=&quot;produktsida.asp?kategori2=218&quot;
    //           href=&quot;NuclearTesting.htm&quot;
    //           
    //  Non-Matches: U Suck
    private static final String URL_PATTERN10 = "((&quot;|')[a-z0-9\\/\\.\\?\\=\\&amp;]*(\\.htm|\\.asp|\\.php|\\.jsp)[a-z0-9\\/\\.\\?\\=\\&amp;]*(&quot;|'))|(href=*?[a-z0-9\\/\\.\\?\\=\\&amp;&quot;']*)";
    
    
    /**
     * validateURL() checks if a string
     * contains a URL, URI, URN
     * @param input the input string.
     * @return true or false
     * @throws Exception 
     */
    public static boolean validateURL(String input) {
        boolean match;
        
        // we check the 10 url patterns to make sure
        // we actually match (or not) a url, uri, urn
        Pattern url1 = Pattern.compile(URL_PATTERN1);
        Pattern url2 = Pattern.compile(URL_PATTERN2);
        Pattern url3 = Pattern.compile(URL_PATTERN3);
        Pattern url4 = Pattern.compile(URL_PATTERN4);
        Pattern url5 = Pattern.compile(URL_PATTERN5);
        Pattern url6 = Pattern.compile(URL_PATTERN6);
        Pattern url7 = Pattern.compile(URL_PATTERN7);
        Pattern url8 = Pattern.compile(URL_PATTERN8);
        Pattern url9 = Pattern.compile(URL_PATTERN9);
        Pattern url10 = Pattern.compile(URL_PATTERN10);
        
        Matcher matchUrl1 = url1.matcher(input);
        Matcher matchUrl2 = url2.matcher(input);
        Matcher matchUrl3 = url3.matcher(input);
        Matcher matchUrl4 = url4.matcher(input);
        Matcher matchUrl5 = url5.matcher(input);
        Matcher matchUrl6 = url6.matcher(input);
        Matcher matchUrl7 = url7.matcher(input);
        Matcher matchUrl8 = url8.matcher(input);
        Matcher matchUrl9 = url9.matcher(input);
        Matcher matchUrl10 = url10.matcher(input);

        if (matchUrl1.find() || matchUrl2.find() || matchUrl3.find() || matchUrl4.find()
                             || matchUrl5.find() || matchUrl6.find() || matchUrl7.find()
                             || matchUrl8.find() || matchUrl9.find() || matchUrl10.find()){
            match = true;
        } else {
            match = false;
        }
        
        return match;
    }
    
    /**
     * validateEmail() checks if a string
     * contains an email address
     * @param input the input string
     * @return true of false
     * @throws Exception 
     */
    public static boolean validateEmail(String input) {
        boolean match;
        
        Pattern email = Pattern.compile(EMAIL_PATTERN); 
        Matcher matchEmail = email.matcher(input); 
        if (matchEmail.find()){
          match = true;
      } else {
            match = false;
        }
       return match; 
    }    
}
