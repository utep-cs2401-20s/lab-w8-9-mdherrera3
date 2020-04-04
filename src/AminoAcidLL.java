class AminoAcidLL {
    char aminoAcid;
    String[] codons;
    int[] counts;
    AminoAcidLL next;

    AminoAcidLL() {
    }


    /********************************************************************************************/
    /* Creates a new node, with a given amino acid/codon
     * pair and increments the codon counter for that codon.
     * NOTE: Does not check for repeats!! */
    AminoAcidLL(String inCodon) {
        aminoAcid = AminoAcidResources.getAminoAcidFromCodon(inCodon);
        codons = AminoAcidResources.getCodonListForAminoAcid(aminoAcid);
        counts = new int[codons.length];
        incrementCodons(inCodon);
        next = null;
    }

    /********************************************************************************************/

    private void incrementCodons(String inCodon) {
        for (int i = 0; i < codons.length; i++) {
            if (codons[i].equals(inCodon)) {
                counts[i] += 1;
            }
        }
    }

    /********************************************************************************************/
    /* Recursive method that increments the count for a specific codon:
     * If it should be at this node, increments it and stops,
     * if not passes the task to the next node.
     * If there is no next node, add a new node to the list that would contain the codon.
     */
    private void addCodon(String inCodon) {
        if (aminoAcid == AminoAcidResources.getAminoAcidFromCodon(inCodon)) {
            incrementCodons(inCodon);
        } else if (next != null) {
            next.addCodon(inCodon);
        } else {
            next = new AminoAcidLL(inCodon);
        }
    }


    /********************************************************************************************/
    /* Shortcut to find the total number of instances of this amino acid */
    private int totalCount() {
        int sum = 0;
        for (int i = 0; i < counts.length; i++) {
            sum += counts[i];
        }
        return sum;
    }

    /********************************************************************************************/
    /* helper method for finding the list difference on two matching nodes
     *  must be matching, but this is not tracked */
    private int totalDiff(AminoAcidLL inList) {
        return Math.abs(totalCount() - inList.totalCount());
    }


    /********************************************************************************************/
    /* helper method for finding the list difference on two matching nodes
     *  must be matching, but this is not tracked */
    private int codonDiff(AminoAcidLL inList) {
        int diff = 0;
        for (int i = 0; i < codons.length; i++) {
            diff += Math.abs(counts[i] - inList.counts[i]);
        }
        return diff;
    }

    /********************************************************************************************/
    /* Recursive method that finds the differences in **Amino Acid** counts.
     * the list *must* be sorted to use this method */
    public int aminoAcidCompare(AminoAcidLL inList) {
        //calling sort to sort the Linked List then can go through using recursion
        if(!inList.isSorted()){
            inList.sort(inList);
        }
        //checking to see if both lists have ended
        if(inList.next == null && next == null){
            return codonDiff(inList);
        }
        //checks to see if the animoacid list is finished
        if(next == null){
            return inList.totalCount() + aminoAcidCompare(inList);
        }
        //checks to see if the amino acid list given is finished
        if(inList.next == null){
            return totalCount() + next.aminoAcidCompare(inList);
        }
        return totalDiff(inList) + next.aminoAcidCompare(inList.next);

    }

    /********************************************************************************************/
    /* Same ad above, but counts the codon usage differences
     * Must be sorted. */
    public int codonCompare(AminoAcidLL inList) {
        //sorts the list if it was not sorted
        if(!inList.isSorted()){
            inList.sort(inList);
        }
        //both are finished going through the codon and this is a base case
        if(inList.next == null && next == null){
            return codonDiff(inList);
        }
        //goes though the starting Linked list
        if(next == null){
            return inList.totalCount() + codonCompare(inList);
        }
        //goes through the list given
        if(inList.next == null){
            return totalCount() + codonCompare(inList);
        }
        return totalDiff(inList) + next.codonCompare(inList);
    }


    /********************************************************************************************/
    /* Recursively returns the total list of amino acids in the order that they are in in the linked list. */
    public char[] aminoAcidList() {
        char[] ret;
        //checks to see if the LL is complete
        if (next == null) {
            return new char[]{aminoAcid};
        }
        //if list is not finished going through then it adds it to the current array after placing it at the start
        else {
            char[] a = next.aminoAcidList();
            ret = new char[a.length + 1];
            ret[0] = aminoAcid;
            for (int i = 1; i < ret.length - 1; i++) {
                ret[i] = a[i - 1];

            }
        }
        return ret;
    }

    /********************************************************************************************/
    /* Recursively returns the total counts of amino acids in the order that they are in in the linked list. */
    public int[] aminoAcidCounts() {
        int[] remain;
        //base case
        if(next == null){
            return new int[totalCount()];
        }
        else {
            int[] amino = next.aminoAcidCounts();
            remain = new int[amino.length + 1];
            remain[0] = totalCount();
            for (int i = 1; i < remain.length; i++) {
                remain[i] = amino[i];
            }
        }
        return remain;
    }


    /********************************************************************************************/
    /* recursively determines if a linked list is sorted or not */
    public boolean isSorted() {
        //base case
        if(next == null){
            return true;
        }
        if(aminoAcid > next.aminoAcid){
            return false;
        }

        return next.isSorted();
    }


    /********************************************************************************************/
    /* Static method for generating a linked list from an RNA sequence */
    public static AminoAcidLL createFromRNASequence(String inSequence) {
        AminoAcidLL head = new AminoAcidLL();
        boolean test = true;
        if (inSequence.substring(0, 3).charAt(0) == '*') {
            head.addCodon(inSequence.substring(0, 3));
            test = false;
        } else {
            head.addCodon(inSequence.substring(0, 3));
        }
        for (int i = 3; i < inSequence.length() - 2 && test; i += 3) {
            System.out.println(inSequence.substring(i, i + 3));
            if (inSequence.charAt(i) == '*') {
                test = false;
            } else {
                head.addCodon(inSequence.substring(i, i + 3));
            }
        }
        return head;
    }


    /********************************************************************************************/
    /* sorts a list by amino acid character*/
    public static AminoAcidLL sort(AminoAcidLL inList) {
        //makes sur the list properly ends even with an empty list
        if(inList == null || inList.next == null) {
            return inList;
        }
        char temp;
        //selection sort method
        for(AminoAcidLL i = inList; i.next != null; i = i.next){
            for(AminoAcidLL k = i.next; k != null; k = k.next){
                temp = i.aminoAcid;
                i.aminoAcid = k.aminoAcid;
                k.aminoAcid = temp;
            }
        }

        return inList;
    }
}

