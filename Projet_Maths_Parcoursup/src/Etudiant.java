import java.util.List;

public class Etudiant {

    private String nom;
    private List<Formation> preferences;

    public Etudiant(String nom, List<Formation> preferences) {
        this.nom = nom;
        this.preferences = preferences;
    }

    public Etudiant(String nom) {
        this.nom = nom;
        this.preferences = null;
    }

    public String getNom() {
        return nom;
    }


    public List<Formation> getPreferences() {
        return preferences;
    }

    public void addPreferences(Formation f) {
        this.preferences.add(f);
    }

    /**
     *
     * @param f1 formation 1
     * @param f2 formation 2
     * @return true si l'etudiant prefere la formation f2, false si il prefer la formation f1
     */
    private boolean prefereFormation(Formation f1, Formation f2) {

        for (Formation f : preferences) {

            // f2 viens avant f1
            if (f == f2) {
                return  true;
            }

            // f1 viens avant f2
            if (f == f1) {
                return false;
            }
        }
        return false;
    }
}
