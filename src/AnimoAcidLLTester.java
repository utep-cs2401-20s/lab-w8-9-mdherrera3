import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnimoAcidLLTester {
    @Test
    public void testRNA(){
        String test1 = ("GCUACGGAG");
        AminoAcidLL list1 = AminoAcidLL.createFromRNASequence(test1);
    }
    @Test
    public void testAminoAcidList(){
        AminoAcidLL list = AminoAcidLL.createFromRNASequence("GCCAGGAGCAGC");
        char[] exp = {'A','R', 'S', 'S'};
        assertArrayEquals(exp,list.aminoAcidList());
        //The Test fails due to it not properly placing the 'A' in the array
        //The output is {,R,S,S}
    }
    @Test
    public void testAminoAcidList2(){
        AminoAcidLL list = AminoAcidLL.createFromRNASequence("GGG");
        char[] exp = {'G'};
        assertArrayEquals(exp,list.aminoAcidList());
        //Test fails as it is unable to even register there is a length of at least one
        //should have output G but does not even output an array
    }
    @Test
    public void testCompareCodon(){
        AminoAcidLL list1 = AminoAcidLL.createFromRNASequence("GCCAGGAGCAGC");
        int exp = 1;
        //assertEquals(exp,AminoAcidLL.codonCompare(list1));
    //not correctly coded
    }
    @Test
    public void testAminoAcidsort(){
        AminoAcidLL test = AminoAcidLL.createFromRNASequence("GCCACGAGCAGC");//ATSS
        char [] exp = {'A','S','S','T'};
        //assertArrayEquals(exp,AminoAcidLL.sort(test));
        //not correctly coded
    }
    @Test
    public void testAminoAcidsort1(){
        AminoAcidLL.createFromRNASequence("GCCAGGAGCAGC");

    }
    @Test
    public void testIsSorted(){
        AminoAcidLL test = AminoAcidLL.createFromRNASequence("GCUACGGAGCUUCGGAGC");
        assertEquals(false,test.isSorted());
        //Test Passes as the AminoAcid is not in order
        //expected false was output false
    }
    @Test
    public void testIsSorted1(){
        AminoAcidLL test = AminoAcidLL.createFromRNASequence("GCGGACCACUCG");
        assertEquals(true,test.isSorted());
        //Test Passes as the AminoAcid is in order
        //expected true was output true
    }
    @Test
    public void testAminoAcidCompare(){

    }
    @Test
    public void testAminoAcidCounts(){
        AminoAcidLL test = AminoAcidLL.createFromRNASequence("GCCACGAGCAGC");
        int [] exp = {1,1,2};
        assertArrayEquals(exp,test.aminoAcidCounts());
        //Test failed
        //I get and error out of bounds and should get 1,1,2
    }
    @Test
    public void testAminoAcidCounts1(){
        AminoAcidLL test = AminoAcidLL.createFromRNASequence("GGGGGGAGGAGGGCG");//GGRRA
        int [] exp = {2,2,1};
        assertArrayEquals(exp,test.aminoAcidCounts());
        //Test failed
        //I get and error out of bounds and should get 1,1,2
    }
}
