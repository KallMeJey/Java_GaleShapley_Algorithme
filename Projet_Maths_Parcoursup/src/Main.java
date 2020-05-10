import java.util.*;

public class Main {

    private static List<String> etudiants = Arrays.asList("Oscar", "Jeneifan", "Clara", "Mael");
    private static List<String> formations = Arrays.asList("DUT Informatique", "DUT GEA", "Ecole de commerce", "Ecole d'ingenieur");

    private static Map<String, List<String>> preferencesEtudiants =
            new HashMap<String, List<String>>() {{
                put("Oscar",
                        Arrays.asList("DUT Informatique", "Ecole d'ingenieur", "DUT GEA", "Ecole de commerce"));
                put("Jeneifan",
                        Arrays.asList("DUT Informatique", "DUT GEA", "Ecole de commerce", "Ecole d'ingenieur"));
                put("Clara",
                        Arrays.asList("DUT GEA", "DUT Informatique", "Ecole de commerce", "Ecole d'ingenieur"));
                put("Mael",
                        Arrays.asList("DUT Informatique", "Ecole d'ingenieur", "Ecole de commerce", "DUT GEA"));
            }};

    private static Map<String, List<String>> preferencesFormations =
            new HashMap<String, List<String>>() {{
                put("DUT Informatique",
                        Arrays.asList("Oscar", "Jeneifan", "Clara", "Mael"));
                put("DUT GEA",
                        Arrays.asList("Clara", "Mael", "Oscar", "Jeneifan"));
                put("Ecole de commerce",
                        Arrays.asList("Mael", "Oscar", "Jeneifan", "Clara"));
                put("Ecole d'ingenieur",
                        Arrays.asList("Mael", "Jeneifan", "Clara", "Oscar"));
            }};


    public static void main(String[] args) {

        Map<String, String> matches = match(etudiants, preferencesEtudiants, preferencesFormations);

        for (Map.Entry<String, String> couple : matches.entrySet()) {
            System.out.println(
                    couple.getKey() + " accueille " + couple.getValue());
        }

        if (checkMatches(etudiants, formations, matches, preferencesEtudiants, preferencesFormations)) {
            System.out.println("Les marriages sont stables");
        } else {
            System.out.println("Les marriages ne sont pas stables");
        }
    }

    private static Map<String, String> match(List<String> etudiants,
                                             Map<String, List<String>> preferencesEtudiants,
                                             Map<String, List<String>> preferencesFormations) {

        Map<String, String> marriagesStables = new TreeMap<>();
        List<String> etudiantsLibres = new LinkedList<>(etudiants);

        while (!etudiantsLibres.isEmpty()) {

            String etu1 = etudiantsLibres.remove(0);
            List<String> etuPrefere = preferencesEtudiants.get(etu1);

            for (String formation : etuPrefere) {
                if (marriagesStables.get(formation) == null) {
                    marriagesStables.put(formation, etu1);
                    break;
                } else {
                    String etu2 = marriagesStables.get(formation);
                    List<String> thisGirlPrefers = preferencesFormations.get(formation);
                    if (thisGirlPrefers.indexOf(etu1) < thisGirlPrefers.indexOf(etu2)) {

                        // La formation prefere cet etudiant a l'etudiant qu'elle a deja
                        marriagesStables.put(formation, etu1);
                        etudiantsLibres.add(etu2);
                        break;
                    }
                }
            }
        }
        return marriagesStables;
    }



    private static boolean checkMatches(List<String> etudiants, List<String> formations,
                                        Map<String, String> marriagesStables, Map<String, List<String>> preferencesEtudiants,
                                        Map<String, List<String>> preferencesFormations) {

        if (!marriagesStables.keySet().containsAll(formations)) {
            return false;
        }

        if (!marriagesStables.values().containsAll(etudiants)) {
            return false;
        }

        Map<String, String> marriagesInverses = new TreeMap<>();

        for (Map.Entry<String, String> m : marriagesStables.entrySet()) {
            marriagesInverses.put(m.getValue(), m.getKey());
        }

        for (Map.Entry<String, String> m : marriagesStables.entrySet()) {

            List<String> formationPrefs = preferencesFormations.get(m.getKey());

            List<String> formationPrefere = new LinkedList<>(formationPrefs.subList(0, formationPrefs.indexOf(m.getValue())));

            List<String> etudiantPrefs = preferencesEtudiants.get(m.getValue());

            List<String> etudiantPrefere = new LinkedList<>(etudiantPrefs.subList(0, etudiantPrefs.indexOf(m.getKey())));

            for (String e : formationPrefere) {

                String etuMarriage = marriagesInverses.get(e);
                List<String> etuPrefere = preferencesEtudiants.get(e);

                if (etuPrefere.indexOf(etuMarriage) > etuPrefere.indexOf(m.getKey())) {
                    System.out.printf("%s prefere %s a %s et %s"
                                    + " prefere %s a sa formation actuelle\n",
                            m.getKey(), e, m.getValue(),
                            e, m.getKey());
                    return false;
                }
            }

            for (String formation : etudiantPrefere) {

                String formationMarriage = marriagesStables.get(formation);
                List<String> formationPreferes = preferencesFormations.get(formation);

                if (formationPreferes.indexOf(formationMarriage) > formationPreferes.indexOf(m.getValue())) {
                    System.out.printf("%s prefere %s a %s et %s"
                                    + " prefere %s a son etudiant actuel\n",
                            m.getValue(), formation, m.getKey(),
                            formation, m.getValue());
                    return false;
                }
            }
        }
        return true;
    }
}
