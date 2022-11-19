package tippetytap;
import java.util.Vector;

class Timing{
    Vector<Character> chars = new Vector<Character>();
    Vector<Long> pressTimes = new Vector<Long>();
    Vector<Long> releaseTimes = new Vector<Long>();

    Timing(){}

    public void add(Character ch, Long pressTime, Long releaseTime){
        chars.add(ch);
        pressTimes.add(pressTime);
        releaseTimes.add(releaseTime);
    }
}



