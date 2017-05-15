/**
 * New class because maybe we will share in the future the data in the Internet or save to database
 */
public class Results {

    private int XScore;
    private int OScore;

    public int getXScore() {
        return XScore;
    }

    public void setXScore(int XScore) {
        this.XScore = XScore;
    }

    public void incXScore() {
        XScore++;
    }

    public void incOScore() {
        OScore++;
    }

    public int getOScore() {
        return OScore;
    }

    public void setOScore(int OScore) {
        this.OScore = OScore;
    }

}
