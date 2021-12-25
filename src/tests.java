public class tests {
    public static void main(String[] args){
        MEE tests = new MEE(new int[] {1,1,2,1,1,1,1,1,1,1,1,1,1,50,1,1,1,1,1,1,1,1,1,1,1,1});
        MEE testsVide = new MEE(new int[] {1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
        int[] pts = new int[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
        /*Plateau p = new Plateau();
        System.out.println(p);
        System.out.println(p.placementValide("ABC", 7, 5, 'h', tests));
        p.place("ABC", 7, 5, 'h', tests);
        System.out.println(p);*/

        System.out.println(tests.retireAleat());
        System.out.println(testsVide.transfereAleat(tests, 100));
    }
}
