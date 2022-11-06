import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Parser {

  public static List<Lexem> Parse(String all){
    var lexems = new ArrayList<Lexem>();
    var regexPattern = 
                      "([0-9]+)|"+ // 1 - number
                      "(#.*\\n)|"+ // 2 - comment
                      "((?:r?'(?:.*[^\\\\])?')|(?:r?\"(?:.*[^\\\\])?\"))|"+ // 3 - string 
                      "(import|class|def|return|if|for|while|yield)|"+ // 4 - keyword
                      "([!=+\\-*/><]=?)|"+ // 5 - operator
                      "([a-zA-Z_][a-zA-Z0-9_]*)|"+ // 6 - identifier
                      "(;|:|,|\\.|\\(|\\)|\\[|\\])|"+ // 7 - punctuatian
                      "(\\S)"; // 8 - non recognizable symbol
    var matcher = Pattern.compile(regexPattern).matcher(all);
    int groupCount = matcher.groupCount();
    while(matcher.find()){
      var lexem = new Lexem();
      for (int i = 1; i <= groupCount; i++) {
        if(matcher.group(i) != null){
          var notation = matcher.group(i);
          switch (i){
            case 1:
              lexem.LexemType = LexemType.Number;
              break;
            case 2:
              lexem.LexemType = LexemType.Comment;
              notation = notation.substring(0, notation.length()-2);
              break;
            case 3:
              lexem.LexemType = LexemType.String;
              break;
            case 4:
              lexem.LexemType = LexemType.Keyword;
              break;
            case 5:
              lexem.LexemType = LexemType.Operator;
              break;
            case 6:
              lexem.LexemType = LexemType.Identificator;
              break;
            case 7:
              lexem.LexemType = LexemType.Punctuation;
              break;
            case 8:
              lexem.LexemType = LexemType.NonRecognizable;
              break;
            default:
              break;
          }
          lexem.Notation = notation;
          break;
        }
      }
      lexems.add(lexem);
    }

    return lexems;
  }
  
}
