import java.util.Arrays;

public class Hash {
	byte[] data; //decaring global varibales and attributes.

	public Hash(byte[] data) { //constructor for Hash. Creates a hash with given byte []
		this.data = data;
	}

	public byte[] getData() { //returns the data for a given hash
		return this.data;
	}

	public boolean isValid() { //returns true iff the data is valid, i.e. the first 6 digits (since hash is in hexadecimal) is a zero.
		return (data[0] == 0 && data[1] == 0 && data[2] == 0);
	}


	public String toString() { //returns the hexadecimal formatted string of the data.
		String result = "";
		for (int i = 0; i < data.length; i++) { //convert each element in the byte[] with for loop
			String checkStr = Integer.toHexString(Byte.toUnsignedInt(data[i])); //convert to string
			if (checkStr.length() < 2) { //if string represents values less than 15 in decimal, append a zero in front.
				checkStr = "0" + checkStr;
			}
			result += checkStr;
		}
		return result;

	}

	public boolean equals(Object other) { //return true iff the two objects, here hashes, are structually equal.
		if (other instanceof Hash) { //check if 'other' is of type Hash.
			Hash o = (Hash) other; 
			return Arrays.equals(this.data, o.data); //see if the two arguments are equal
		}
		return false;
	}
}
