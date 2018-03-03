
import java.nio.ByteBuffer;
import java.security.*;

public class Block {
	int num, amount; //declaring global variables to define the attributes of the Block class
	Hash prevHash, hash;
	long nonce;

	public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException { //constructor to create a block without previous nonce used in mining nonce
		this.num = num;
		this.amount = amount;
		this.prevHash = prevHash;
		for (long var = 0L;; var++) { //run through a loop to return a valid nonce value
			Hash hash = new Hash(calculateHash(num, amount, prevHash, var)); //calculate hash value and check if it is valid to return the nonce value accordingly
			if (hash.isValid()) { //calling method of the Hash class to check if this hash is valid
				this.nonce = var;
				this.hash = hash;
				break;
			}

		}
	}

	public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException { //constructor to create a block with a given nonce value used for appending a block to a block chain
		this.num = num;
		this.amount = amount;
		this.prevHash = prevHash;
		this.nonce = nonce;
		Hash hash = new Hash(calculateHash(num, amount, prevHash, nonce));
		this.hash = hash;
	}

	public byte[] calculateHash(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException { //calculate hash using messagedigest class
		byte[] hash;
		MessageDigest md = MessageDigest.getInstance("sha-256");
		md.update(ByteBuffer.allocate(4).putInt(num).array()); //updating the hash with values to make it unique
		md.update(ByteBuffer.allocate(4).putInt(amount).array());
		if (num != 0) {
			md.update(prevHash.data);
		}
		md.update(ByteBuffer.allocate(8).putLong(nonce).array());
		hash = md.digest();
		return hash;
	}

	public int getNum() { //returns the number of the block
		return num;
	}

	public int getAmount() { //returns the amount of a block
		return amount;
	}

	public long getNonce() { //returns the nonce value of a block
		return nonce;
	}

	public Hash getPrevHash() { //returns the hash of the block before the current block
		return prevHash;
	}

	public Hash getHash() { //returns the hash of the current block
		return hash;
	}

	public String toString() { //concatenates all the data of the block in a string and returns it
		return "Block " + num + " (Amount: " + amount + ", Nonce: " + nonce + ", prevHash: " + prevHash + ", hash: "
				+ hash + ")";
	}
}
