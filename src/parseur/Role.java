package parseur;

/**
 * TP2 - IFT 3913 (Qualités métriques)
 * Mohamed Sarr & Kevin P. Kombate
 */

public class Role {

    String name;
    String multiplicity;
    static String [] VALID_MULTIPLICITY = {
            "ONE",
            "MANY",
            "ONE_OR_MANY",
            "OPTIONALLY_ONE",
            "UNDEFINED",
    };

    /**
     * Constructeur
     * @param name
     * @param multiplicity
     */
    public Role(String name, String multiplicity){
        this.name = name;
        this.multiplicity = multiplicity;
    }

    /**
     * Verification de la multuplicite
     * @param multiplicity
     * @return
     */
    public static boolean isMultiplicityValid (String multiplicity){
        for(int i = 0; i < VALID_MULTIPLICITY.length; i++){
            if(multiplicity.equals(VALID_MULTIPLICITY[i])){
                return true;
            }
        }
        return false;
    }

    /**
     * Get name
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     * Get multuplicity
     * @return
     */
    public String getMultiplicity(){
        return this.multiplicity;
    }
}
