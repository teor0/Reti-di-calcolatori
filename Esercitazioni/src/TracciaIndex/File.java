package TracciaIndex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class File implements Serializable{
    
    private String filename;
    private String[] keywords;
    private String contenuto;


    public File(String filename, String[] keywords, String contenuto){
        this.filename=filename;
        this.keywords=keywords;
        this.contenuto=contenuto;
    }

    public String getFilename() {
        return filename;
    }

    public String[] getKeywords() {
        return keywords;
    }

    @Override
    public String toString() {
        String r="Nome file "+ filename +"\n"+
            "Parole chiavi "+ Arrays.toString(keywords)+ "\n"+
            "Contenuto "+ contenuto;
        return r;
    }

}
