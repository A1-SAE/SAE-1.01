public class Plateau {
    private Case[][] g; // g pour grille
    private static int[][] casesSpeciales = new int[][] {
            {0,0,5},
            {1,1,4},
            {2,2,4},
            {3,3,4},
            {4,4,4},
            {5,5,3},
            {6,6,2},
            {7,7,4},
            {0,3,2},
            {0,7,5},
            {1,5,3},
            {2,6,2},
            {3,7,2}
    };

    public Plateau(){
        this.g = new Case[15][15];
        for(int i = 0; i < g.length; i++){
            for(int j = 0; j < g[0].length; j++){
                g[i][j] = new Case(0);
            }
        }

        //R = 0°
        fillPlateau(g, casesSpeciales);

        //R = 45°
        for(int i=0; i < casesSpeciales.length; i++){
            casesSpeciales[i][1] = 14 - casesSpeciales[i][1];
        }
        fillPlateau(g, casesSpeciales);

        //R = 90°
        for(int i=0; i < casesSpeciales.length; i++){
            casesSpeciales[i][0] = 14 - casesSpeciales[i][0];
            casesSpeciales[i][1] = 14 - casesSpeciales[i][1];
            int temp = casesSpeciales[i][0];
            casesSpeciales[i][0] = casesSpeciales[i][1];
            casesSpeciales[i][1] = temp;
        }
        fillPlateau(g, casesSpeciales);

        //R = 135°
        for(int i=0; i < casesSpeciales.length; i++){
            casesSpeciales[i][0] = 14 - casesSpeciales[i][0];
        }
        fillPlateau(g, casesSpeciales);

        //R = 180°
        for(int i=0; i < casesSpeciales.length; i++){
            int temp = casesSpeciales[i][0];
            casesSpeciales[i][0] = casesSpeciales[i][1];
            casesSpeciales[i][1] = temp;
        }
        fillPlateau(g, casesSpeciales);

        //R = 225°
        for(int i=0; i < casesSpeciales.length; i++){
            casesSpeciales[i][1] = 14 - casesSpeciales[i][1];
        }
        fillPlateau(g, casesSpeciales);

        //R = 270°
        for(int i=0; i < casesSpeciales.length; i++){
            casesSpeciales[i][0] = 14 - casesSpeciales[i][0];
            casesSpeciales[i][1] = 14 - casesSpeciales[i][1];
            int temp = casesSpeciales[i][0];
            casesSpeciales[i][0] = casesSpeciales[i][1];
            casesSpeciales[i][1] = temp;
        }
        fillPlateau(g, casesSpeciales);

        //R = 315°
        for(int i=0; i < casesSpeciales.length; i++){
            casesSpeciales[i][0] = 14 - casesSpeciales[i][0];
        }
        fillPlateau(g, casesSpeciales);
    }

    public static Case[][] fillPlateau(Case[][] g, int[][] schema){
        for(int i = 0; i < Plateau.casesSpeciales.length; i++){
            if(g[Plateau.casesSpeciales[i][0]][Plateau.casesSpeciales[i][1]].getCouleur() == 0){
                g[Plateau.casesSpeciales[i][0]][Plateau.casesSpeciales[i][1]] = new Case(Plateau.casesSpeciales[i][2]);
            }
        }

        return g;
    }

    public String toString(){
        String res = "";
        for(int i = 0; i < this.g.length; i++){
            for(int j = 0; j < this.g[0].length; j++){
                res += " " + this.g[i][j].getCouleur() + " ";
            }
            res += "\n";
        }

        return res;
    }

    public static void main(String[] args){
        Plateau p = new Plateau();
        System.out.println(p);
    }

}