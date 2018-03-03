import java.security.NoSuchAlgorithmException;

public class BlockChain {
	Node first; //declaring global variables to define the attributes of the Block Class.
	Node last; 
	int size = 0;

	public class Node { //constructor for a node. Contains a block and the next node.
		Block block;
		Node next;
	}

	public BlockChain(int initial) throws NoSuchAlgorithmException { //constructor for a blockchain, given an initial amount.
		first = new Node();
		first.block = new Block(0, initial, null);
		first.next = null;
		last = first;
		this.size++;
	}

	public Block mine(int amount) throws NoSuchAlgorithmException { //returns a block with a valid hash given an amount.
		Block blk = new Block(this.size, amount, first.block.hash);
		return blk;
	}

	public int getSize() { //returns the size of the Blockchain.
		return size;
	}

	public void append(Block blk) { //appends the given block to an existing blockchain.
		if (blk.getHash().isValid()) { //check to make sure block is valid.
            last.next = new Node(); //create space for node
			last.next.block = blk; //append node
            last = last.next; //move last to the next
            this.size++; //increment size
		} else {
			throw new IllegalArgumentException("Invalid Block."); //Throw an error message if there is an illegal argument
		}
	}

	public boolean removeLast() { //remove last element of the blockchainn
		if (size == 1) { //if size is 1, we cannot return anything
			return false;
		} else {
			Node check = first;
			while (check.next != last) { //while loop to look for the last node
				check = check.next;
			}
			check.next = null; //remove the block and assign null
			last = check; //reassign last to the second to last block
            size--; //decrement size 
			return true;
		}
	}

	public Hash getHash() { //return the hash of a block.
		return last.block.getHash();
	}

	public boolean isValidBlockChain() { // returns true iff the blockchain is
											// valid. i.e. the balance of the
											// account cannot go negative at any
											// point.
		int balance = 0;
		Node cur = first;
		for (int i = 0; i < size; i++) { // go through the linked list and add
											// the amount to balance.
			balance += cur.block.amount;
			cur = cur.next;
			if (balance < 0) { // if we encounter a negative balance, return
								// false, since this indicates an invalid
								// blockchain.
				return false;
			}
		}
		Node node1 = first;
		Node node2 = first.next;
		for (int j = 0; j < size - 1; j++) {
			if (!node1.block.hash.equals(node2.block.prevHash)) { //check if the prehash of a given block and the hash of the previous block is identical
				return false;
			}
			node1 = node1.next; //move node1 and node2 to next.
			node2 = node2.next;
		}
		return true;
	}

	public void printBalances() { //print out the balance of alice and bob with a specific format. 
        int balance = 0;
        Node cur = first;
        for (int i = 0; i < size; i++) { //go throught the blockchain and compute the balance
			balance += cur.block.amount;
			cur = cur.next;
		}   
		int alice = balance;
		int bob = first.block.amount - balance;
		System.out.println("Alice: " + alice + ", Bob: " + bob); //print message
	}

	public String toString() { //print the information in all blocks of the blockchain. 
		Node cur = first;
		String ret = "";
		while (cur.next != null) { //go through until the end of the blockchain, printing out information for each block
			ret += "\n"+cur.block.toString(); //add newline character and the information of the block
			cur = cur.next;
		}
		ret += "\n"+cur.block.toString(); //add information for the last block
		return ret;
	}
}
