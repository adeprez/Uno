package fr.utt.lo02.uno.io;

import fr.utt.lo02.uno.io.interfaces.IOable;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe representant un ensemble d'octets. Celle-ci permet de manipuler divers types de donnees
 * @see IOable
 */
public class IO implements IOable {
	public static final byte VRAI = 0, FAUX = 1, LIMITE_BYTE_MAX = 127, LIMITE_BYTE_MIN = -128;
	public static final int LIMITE_BYTE_POSITIF = 255, LIMITE_SHORT_MAX = 65535, LIMITE_3_BYTES = 8388607,
			LIMITE_3_BYTES_POSITIF = 16777215, LIMITE_SHORT_STRING = LIMITE_BYTE_POSITIF, LIMITE_STRING = LIMITE_SHORT_MAX;
	private final List<Byte> buffer;
	private byte[] build;
	private int index;
	private boolean builded;


	public IO(byte... bytes) {
		buffer = new ArrayList<Byte>();
		addBytes(bytes);
		build = bytes;
		builded = true;
	}
	
	public IO(IO io) {
		buffer = io.buffer;
		build = io.build;
		builded = io.builded;
	}

	/**
	 * @param index la position du curseur pour la lecture de donnees
	 */
	public IO setIndex(int index) {
		this.index = index;
		return this;
	}
	
	/**
	 * @return l'index du curseur pour la lecture de donnees
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Ajoute un octet (8 bits) compris entre -128 et 127
	 * @param b l'octet a ajouter
	 * @return cet objet IO
	 * @see #next()
	 */
	public IO addByte(byte b) {
		buffer.add(b);
		builded = false;
		return this;
	}
	
	/**
	 * Ajoute un octet (8 bits)
	 * @param b l'octet a ajouter
	 * @return cet objet IO
	 * @throws IllegalArgumentException si l'entier n'est pas compris entre -128 et 127
	 * @see #next()
	 */
	public IO addByte(int b) {
		check(b, LIMITE_BYTE_MIN, LIMITE_BYTE_MAX);
		return addByte((byte) b);
	}

	/**
	 * Ajoute une chaine de caracteres (4 octets plus la taille de la chaine) de taille maximale 2147483647
	 * @param str la chaine de caracteres a ajouter
	 * @return cet objet IO
	 * @see #nextStringLong()
	 */
	public IO addLong(String str) {
		return add(str.length()).addBytes(str.getBytes());
	}

	/**
	 * Ajoute un entier (4 octets) compris entre 2147483647 et -2147483648
	 * @param i l'entier a ajouter
	 * @return cet objet IO
	 * @see #nextInt()
	 */
	public IO add(int i) {
		return add((byte) (i >> 24)).add((byte) (i >> 16)).add((byte) (i >> 8)).add((byte) (i));
	}

	/**
	 * Ajoute un octet (8 bits)
	 * @param b l'octet a ajouter
	 * @return cet objet IO
	 * @see #next()
	 */
	public IO add(byte b) {
		return addByte(b);
	}

	/**
	 * Ajoute un octet (8 bits). 0 pour vrai, 1 sinon
	 * @param b le booleen a ajouter
	 * @return cet objet IO
	 * @see #nextBoolean()
	 */
	public IO add(boolean b) {
		return add(b ? VRAI : FAUX);
	}
	
	/**
	 * Ajoute un long (8 octets) compris entre 9223372036854775807 et -9223372036854775808
	 * @param l l'entier long a ajouter
	 * @return cet objet IO
	 * @see #nextLong()
	 */
	public IO add(long l) {
		return addBytes(ByteBuffer.allocate(8).putLong(l).array());
	}

	/**
	 * Ajoute le contenu de cet {@link IOable}
	 * @param io le contenu a extraire
	 * @return cet objet IO
	 * @see #getBytes()
	 */
	public IO add(IOable io) {
		return addBytes(io.getBytes());
	}
	
	/**
	 * Ajoute une chaine de caracteres courte (1 octet plus la taille de la chaine)
	 * @param str la chaine de caracteres a ajouter
	 * @return cet objet IO
	 * @throws IllegalArgumentException si la taille de la chaine est superieure a 255
	 * @see #nextShortString()
	 */
	public IO addShort(String str) {
		return addBytePositif(str.length()).addBytes(str.getBytes());
	}
	
	/**
	 * Ajoute un entier positif court, code sur deux octets
	 * @param i l'entier a ajouter
	 * @return cet objet IO
	 * @throws IllegalArgumentException si l'entier est negatif ou sa taille superieure a 65535
	 * @see #nextShortInt()
	 */
	public IO addShort(int i) {
		check(i, 0, LIMITE_SHORT_MAX);
		return add((byte) (i >> 8)).add((byte) (i));
	}
	
	/**
	 * Ajoute une chaine de caracteres (deux octets plus la taille de la chaine)
	 * @param str la chaine de caracteres a ajouter
	 * @return cet objet IO
	 * @throws IllegalArgumentException si la taille de la chaine est superieure a 65535
	 * @see #nextString()
	 */
	public IO add(String str) {
		return addShort(str.length()).addBytes(str.getBytes());
	}

	/**
	 * Ajoute un octet positif (8 bits)
	 * @param b l'octet a ajouter
	 * @return cet objet IO
	 * @throws IllegalArgumentException si la valeur n'est pas comprise entre 0 et 255
	 * @see #nextPositif()
	 */
	public IO addBytePositif(int b) {
		check(b, 0, LIMITE_BYTE_POSITIF);
		return add((byte) (b - LIMITE_BYTE_MAX - 1));
	}

	public IO addIntsPositif(int... ints) {
		for(final int i : ints)
			addShort(i);
		return this;
	}

	public IO addInts(int... ints) {
		for(final int i : ints)
			add(i);
		return this;
	}

	public IO addIntsPositifs(int... ints) {
		for(final int i : ints)
			addShort(i);
		return this;
	}
	
	public IO addShortsPositif(int... ints) {
		for(final int i : ints)
			addShort(i);
		return this;
	}
	
	public IO addStrings(String... str) {
		for(final String s : str)
			add(s);
		return this;
	}

	public IO addShortStrings(String... str) {
		for(final String s : str)
			addShort(s);
		return this;
	}

	public IO addBytes(byte... b) {
		for(final byte bb : b)
			add(bb);
		return this;
	}

	public IO addBytesPositif(int... b) {
		for(final int i : b)
			addBytePositif(i);
		return this;
	}
	
	/**
	 * @return le byte suivant
	 * @throws ArrayIndexOutOfBoundsException si plus aucun octet ne peut etre lu
	 * @see #add(byte)
	 */
	public byte next() {
		return buffer.get(index ++);
	}
	
	/**
	 * @return un octet positif (8 bits) compris entre 0 et 255
	 * @throws ArrayIndexOutOfBoundsException si plus aucun octet positif ne peut etre lu
	 * @see #addBytePositif(int)
	 */
	public int nextPositif() {
		return next() + LIMITE_BYTE_MAX + 1;
	}
	
	/**
	 * @return un entier (4 octets) compris entre 2147483647 et -2147483648
	 * @throws ArrayIndexOutOfBoundsException si plus aucun entier ne peut etre lu
	 * @see #add(int)
	 */
	public int nextInt() {
		return ByteBuffer.wrap(new byte[] {next(), next(), next(), next()}).getInt();
	}
	
	/**
	 * @return un long (8 octets) compris entre 9223372036854775807 et -9223372036854775808
	 * @throws ArrayIndexOutOfBoundsException si plus entier long ne peut etre lu
	 * @see #add(long)
	 */
	public long nextLong() {
		return ByteBuffer.wrap(new byte[] {next(), next(), next(), next(), next(), next(), next(), next()}).getLong();
	}
	
	/**
	 * @return vrai (0) ou faux (1)
	 * @throws ArrayIndexOutOfBoundsException si plus aucun booleen ne peut etre lu
	 * @see #add(boolean)
	 */
	public boolean nextBoolean() {
		return next() == VRAI;
	}
	
	/**
	 * @return une chaine de caracteres de taille 2147483647 maximale
	 * @throws ArrayIndexOutOfBoundsException si plus aucune chaine de caractere longue ne peut etre lue
	 * @see #addLong(String)
	 */
	public String nextStringLong() {
		byte[] b = new byte[nextInt()];
		for(int i=0 ; i<b.length ; i++) 
			b[i] = next();
		return new String(b);
	}
	
	/**
	 * @return une chaine de caractere de taille 255 maximale
	 * @throws ArrayIndexOutOfBoundsException si plus aucune chaine de caractere courte ne peut etre lue
	 * @see #addShort(String)
	 */
	public String nextShortString() {
		byte[] b = new byte[nextPositif()];
		for(int i=0 ; i<b.length ; i++) 
			b[i] = next();
		return new String(b);
	}
	
	/**
	 * @return une chaine de caracteres de taille 65535 maximale
	 * @throws ArrayIndexOutOfBoundsException si plus aucune chaine de caractere ne peut etre lue
	 * @see #add(String)
	 */
	public String nextString() {
		byte[] b = new byte[nextShortInt()];
		for(int i=0 ; i<b.length ; i++) 
			b[i] = next();
		return new String(b);
	}
	
	/**
	 * @return un entier positif compris entre 0 et 65535
	 * @throws ArrayIndexOutOfBoundsException si plus aucun entier court ne peut etre lu
	 * @see #addShort(int)
	 */
	public int nextShortInt() {
		return ByteBuffer.wrap(new byte[] {0, 0, next(), next()}).getInt();
	}
	
	public int[] nextIntsPositifs(int nombre) {
		int[] i = new int[nombre];
		for(int j=0 ; j<i.length ; j++)
			i[j] = nextShortInt();
		return i;
	}
	
	public int[] nextPositifs(int nombre) {
		int[] i = new int[nombre];
		for(int j=0 ; j<i.length ; j++)
			i[j] = nextPositif();
		return i;
	}
	
	public void inserer(int index, byte b) {
		buffer.set(index, b);
	}
	
	public void inserer(int index, long l) {
		byte[] array = ByteBuffer.allocate(8).putLong(l).array();
		for (int i = 0; i < array.length; i++)
			inserer(index + i, array[i]);
	}
	
	public boolean aByte() {
		return index + 1 <= buffer.size();
	}
	
	public boolean aPositif() {
		return aByte();
	}
	
	public boolean aBytes(int nombre) {
		return index + 1 * nombre <= buffer.size();
	}
	
	public boolean aBoolean() {
		return aByte();
	}
	
	public boolean aBooleans(int nombre) {
		return aBytes(nombre);
	}
	
	public boolean aShortInt() {
		return aBytes(2);
	}
	
	public boolean aInt() {
		return aBytes(4);
	}

	public boolean aLong() {
		return aBytes(8);
	}
	
	public boolean aInts(int nombre) {
		return aBytes(4 * nombre);
	}
	
	public boolean aStringLong() {
		if(!aInt())
			return false;
		int tmp = index;
		boolean a = index + nextInt() <= buffer.size();
		setIndex(tmp);
		return a;
	}
	
	public boolean aShortString() {
		if(!aByte())
			return false;
		int tmp = index;
		boolean a = index + nextPositif() <= buffer.size();
		setIndex(tmp);
		return a;
	}
	
	public boolean aString() {
		if(!aShortInt())
			return false;
		int tmp = index;
		boolean a = index + nextShortInt() <= buffer.size();
		setIndex(tmp);
		return a;
	}
	
	/**
	 * Construit le tableau d'octets a partir de la liste variable
	 */
	public void build() {
		build = new byte[buffer.size()];
		for(int i=0 ; i<build.length ; i++) 
			build[i] = buffer.get(i);
		builded = true;
	}

	/**
	 * @return le nombre d'octets contenus dans cet objet
	 */
	public int size() {
		return buffer.size();
	}
	
	/**
	 * @param rang le rang de l'octet a supprimer
	 * @return ce meme objet
	 */
	public IO supprimer(int rang) {
		buffer.remove(rang);
		builded = false;
		return this;
	}
	
	public IO supprimer(int debut, int fin) {
		for(int i=debut ; i<fin - debut ; i++)
			supprimer(debut);
		return this;
	}
	
	/**
	 * Inverse deux octets selon leur position
	 * @param i1 le rang du premier octet
	 * @param i2 le rang du second octet
	 * @return ce meme objet
	 */
	public IO inverser(int i1, int i2) {
		byte tmp = buffer.get(i1);
		buffer.set(i1, buffer.get(i2));
		buffer.set(i2, tmp);
		builded = false;
		return this;
	}
	
	/**
	 * @param nombre le nombre d'octets a ignorer
	 */
	public void passer(int nombre) {
		index += nombre;
	}

	/**
	 * Efface tous les octets contenus dans cet objet
	 */
	public void vider() {
		buffer.clear();
		index = 0;
		builded = false;
	}

	/**
	 * @return un tableau contenant les octets entre la position actuelle et la fin des donnees (de l'index a size() - 1)
	 */
	public byte[] getBytesRestants() {
		return getBytesRestants(index, size() - 1);
	}
	
	/**
	 * @param debut l'index de debut
	 * @param fin l'index de fin
	 * @return un tableau contenant les octets entre les positions precisees
	 */
	public byte[] getBytesRestants(int debut, int fin) {
		byte[] b = new byte[fin - debut];
		for(int i=0 ; i<b.length ; i++)
			b[i] = buffer.get(debut + i);
		return b;
	}
	
	@Override
	public byte[] getBytes() {
		if(!builded)
			build();
		return build;
	}
	
	@Override
	public String toString() {
		return new String(getBytes());
	}
	
	private static void check(int valeur, int min, int max) {
		checkMin(valeur, min);
		checkMax(valeur, max);
	}
	
	private static void checkMax(int valeur, int max) {
		if(valeur > max)
			throw new IllegalArgumentException("Valeur hors limite : " + valeur + " (max=" + max + ")");
	}
	
	private static void checkMin(int valeur, int min) {
		if(valeur < min)
			throw new IllegalArgumentException("Valeur hors limite : " + valeur + " (min=" + min + ")");
	}
	
	/**
	 * Converti un entier positif en un tableau de bytes
	 * @param i l'entier a convertir
	 * @return un tableau de dimension 3 representant un entier
	 * @throws IllegalArgumentException si l'entier est negatif ou superieur a 16777216
	 * @see #getInt(byte...)
	 */
	public static byte[] getBytes(int i) {
		check(i, 0, LIMITE_3_BYTES_POSITIF);
		return new byte[] {(byte) (i >> 16), (byte) (i >> 8), (byte) i};
	}
	
	/**
	 * Converti un tableau d'octets en un entier positif
	 * @param b un tableau de bytes de dimension 3
	 * @return un entier positif compris entre 0 et 16777216
	 * @see #getBytes(int)
	 */
	public static int getInt(byte... b) {
		if(b.length != 3) 
			throw new IllegalArgumentException("Cette methode necessite un tableau de dimension 3");
		int i1 = (b[0] << 16), i2 = (b[1] << 8), i3 = b[2];
		return (i1 < 0 ? i1 + 16777216 : i1) + (i2 < 0 ? i2 + 65536 : i2) + (i3 < 0 ? i3 + 256 : i3);
	}

}
