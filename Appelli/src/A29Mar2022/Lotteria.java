package A29Mar2022;

public class Lotteria {

    private String nome;

    public Lotteria(String nome){
        this.nome=nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if(this==o)
            return true;
        else if(this==null || ! (o instanceof Lotteria))
            return false;
        Lotteria l=(Lotteria) o;
        return this.nome.equals(l.getNome());
    }

    @Override
    public String toString() {
        return "Nome: "+ nome;
    }
    
}
