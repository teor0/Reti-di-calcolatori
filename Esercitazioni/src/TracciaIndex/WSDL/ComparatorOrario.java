import java.util.Comparator;

public class ComparatorOrario implements Comparator<Volo>{

    @Override
    public int compare(Volo v1, Volo v2) {
        if(v1.getOrario().getOre()> v2.getOrario().getOre())
            return 1;
        else if(v1.getOrario().getOre()==v2.getOrario().getOre() && v1.getOrario().getMinuti()==v2.getOrario().getMinuti())
            return 0;
        else if(v1.getOrario().getOre()<v2.getOrario().getOre())
            return -1;
        else if(v1.getOrario().getMinuti()>v2.getOrario().getMinuti())
            return 1;
        else if(v1.getOrario().getMinuti()<v2.getOrario().getMinuti())
            return -1;
        return 0;
    }
}
