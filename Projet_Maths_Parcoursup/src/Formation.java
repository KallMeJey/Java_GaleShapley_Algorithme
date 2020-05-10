import java.util.List;

public class Formation {

    private String description;
    private List<Etudiant> preferences;

    public Formation(String description, List<Etudiant> preferences) {
        this.description = description;
        this.preferences = preferences;
    }

    public Formation(String description) {
        this.description = description;
        this.preferences = null;
    }

    public String getDescription() {
        return description;
    }


    public List<Etudiant> getPreferences() {
        return preferences;
    }

    public void addPreferences(Etudiant e) {
        this.preferences.add(e);
    }
}
