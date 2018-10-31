package game;


public class MyArrayList extends Exception{
    private Object[] elemenData;
    private int size;

    public int size(){
        return size;
    }
    public boolean isEmpty(){
        return size == 0;
    }
    public MyArrayList() {
        this(10);
    }

    public MyArrayList(int initCapatity)  {
        if (initCapatity < 0) {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.elemenData = new Object[initCapatity];
    }
    public boolean add(Object object){
        if (size == elemenData.length) {
            Object[] newobject = new Object[size* 2 + 1];
            System.arraycopy(elemenData, 0, newobject, 0, elemenData.length);
            /*for (int i = 0; i <elemenData.length ; i++) {
                newobject[i] = elemenData[i];
            }

        }*/
            elemenData = newobject;
        }
        elemenData[size++] = object;
        return true;
    }
    public void remove(int index){
        rangeCheck(index);
        int numMoved = size - index -1;
        if (numMoved > 0) {
            System.arraycopy(elemenData,index+1,elemenData,index,numMoved);
            elemenData[--index] = null;
        }
    }
    public void remove(Object o){
        for (int i = 0; i <size ; i++) {
            if (get(i).equals(o)) {
                remove(i);
            }
        }
    }
    public Object set(int index,Object object){
        rangeCheck(index);
        //Object oldValue = elemenData[index];
        elemenData[index] = object;
        return object;
    }
    public Object get(int index){
        rangeCheck(index);
        return elemenData[index];
    }
    public void add(int index,Object object){
        rangeCheck(index);
        int numMoved = size - index;
        if (numMoved > 0) {
            System.arraycopy(elemenData,index,elemenData,index + 1,numMoved);
            elemenData[index] = object;
        }
        size++;
    }
    private void rangeCheck(int index){
        if (index < 0 || index >= size) {
            try {
                throw new MyException("数组下标不存在");
            } catch (MyException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyArrayList list = new MyArrayList();
        list.add("111");
        list.add("44");
        list.add("55");
        list.add("222");
        list.add(2,"000000");
        System.out.println(list.size);
        System.out.println(list.get(2));
    }
}

