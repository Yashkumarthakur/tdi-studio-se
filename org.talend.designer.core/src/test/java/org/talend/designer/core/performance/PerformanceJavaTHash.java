// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.designer.core.performance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.FastHashMap;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.collections.map.ReusableMultiKeyMap;
import org.junit.Before;
import org.junit.Test;
import org.talend.commons.utils.data.map.ReusableMultiKey;
import org.talend.commons.utils.time.TimeMeasure;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.MetadataColumn;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class PerformanceJavaTHash {

    private ArrayList<RowStruct> rowsLookup;

    private ArrayList<Integer> integers;

    private ArrayList<String> strings;

    private static final int TOTAL_ROWS = 4000000;

    // private static final int TOTAL_ROWS = 10;

    private static final int ITERATIONS_FOR_QUICK = TOTAL_ROWS - 1;

    // private static final int ITERATIONS_FOR_SLOW = 50000;
    private static final int ITERATIONS_FOR_SLOW = 10000;

    // private static final int ITERATIONS_FOR_SLOW = 50;

    private static final Object[] ARRAY_ONE_KEY = new Object[1];

    private static final Object[] ARRAY_TWO_KEYS = new Object[2];

    private static final Object[] ARRAY_THREE_KEYS = new Object[3];

    private static final Object[] ARRAY_FOUR_KEYS = new Object[4];

    /**
     * DOC amaumont Comment method "init".
     */
    @Before
    public void init() {

        System.out.println("\n########################################################\n");

        if (rowsLookup == null) {

            ArrayList<IMetadataColumn> columns = new ArrayList<IMetadataColumn>();

            MetadataColumn intKey = new MetadataColumn();
            intKey.setLabel("intKey");
            intKey.setKey(true);
            columns.add(intKey);

            MetadataColumn integerKey = new MetadataColumn();
            integerKey.setLabel("integerKey");
            integerKey.setKey(true);
            columns.add(integerKey);

            MetadataColumn stringKey = new MetadataColumn();
            stringKey.setLabel("stringKey");
            stringKey.setKey(true);
            columns.add(stringKey);

            MetadataColumn name = new MetadataColumn();
            name.setLabel("name");
            name.setKey(false);
            columns.add(name);

            ArrayList<RowStruct> localRowsLookup = new ArrayList<RowStruct>();

            integers = new ArrayList<Integer>();
            strings = new ArrayList<String>();

            for (int i = 0; i < TOTAL_ROWS; i++) {
                localRowsLookup.add(new RowStruct(i, i, String.valueOf(i), String.valueOf(i), "line" + i));
                integers.add(new Integer(i));
                strings.add(String.valueOf(i));
            }
            rowsLookup = localRowsLookup;
        }

    }

    @Test
    public void testWithIntegerKeys() {

        TimeMeasure.begin("testWithIntegerKeys LoadReadExecutorWithEqualsHashCode");
        LoadReadExecutorWithEqualsHashCode executorEqualsHashCode = new LoadReadExecutorWithEqualsHashCode(ITERATIONS_FOR_QUICK);

        {
            // TimeMeasure.begin("executeWithIntegerAndMapAndMultiThreading with HashMap");
            // executorWithout.executeWithIntegerAndMapAndMultiThreading(new HashMap(ITERATIONS_FOR_QUICK + 1));
            // TimeMeasure.end("executeWithIntegerAndMapAndMultiThreading with HashMap");
            //
            // TimeMeasure.begin("executeWithIntegerAndMapAndMultiThreading with HashedMap");
            // executorWithout.executeWithIntegerAndMapAndMultiThreading(new HashedMap(ITERATIONS_FOR_QUICK));
            // TimeMeasure.end("executeWithIntegerAndMapAndMultiThreading with HashedMap");
            //
            // TimeMeasure.begin("executeWithIntegerAndMapAndMultiThreading with FastHashMap");
            // executorWithout.executeWithIntegerAndMapAndMultiThreading(new FastHashMap(ITERATIONS_FOR_QUICK));
            // TimeMeasure.end("executeWithIntegerAndMapAndMultiThreading with FastHashMap");
            //
            // System.out.println("executeWithIntegerAndFastHashMap");
            // executorWithout.executeWithIntegerAndFastHashMap();

            for (int i = 0; i < 10; i++) {
                System.out.println("executeWithIntegerAndHashMap " + i);
                executorEqualsHashCode.executeWithIntegerAndHashMap();
            }

            // System.out.println("executeWithIntegerAndHashedMap");
            // executorWithout.executeWithIntegerAndHashedMap();
        }
        TimeMeasure.end("testWithIntegerKeys LoadReadExecutorWithEqualsHashCode");

        TimeMeasure.begin("testWithIntegerKeys LoadReadExecutorWithoutMultiKey");
        LoadReadExecutorWithoutMultiKey executorWithout = new LoadReadExecutorWithoutMultiKey(ITERATIONS_FOR_QUICK);

        {
            for (int i = 0; i < 10; i++) {
                TimeMeasure.begin("executeWithIntegerAndMapAndMultiThreading with HashMap" + i);
                executorWithout.executeWithIntegerAndMapAndMultiThreading(new HashMap(ITERATIONS_FOR_QUICK));
                TimeMeasure.end("executeWithIntegerAndMapAndMultiThreading with HashMap" + i);
            }

            TimeMeasure.begin("executeWithIntegerAndMapAndMultiThreading with HashedMap");
            executorWithout.executeWithIntegerAndMapAndMultiThreading(new HashedMap(ITERATIONS_FOR_QUICK));
            TimeMeasure.end("executeWithIntegerAndMapAndMultiThreading with HashedMap");

            TimeMeasure.begin("executeWithIntegerAndMapAndMultiThreading with FastHashMap");
            executorWithout.executeWithIntegerAndMapAndMultiThreading(new FastHashMap(ITERATIONS_FOR_QUICK));
            TimeMeasure.end("executeWithIntegerAndMapAndMultiThreading with FastHashMap");

            System.out.println("executeWithIntegerAndFastHashMap");
            executorWithout.executeWithIntegerAndFastHashMap();

            System.out.println("executeWithIntegerAndHashMap");
            executorWithout.executeWithIntegerAndHashMap();

            System.out.println("executeWithIntegerAndHashedMap");
            executorWithout.executeWithIntegerAndHashedMap();
        }
        TimeMeasure.end("testWithIntegerKeys LoadReadExecutorWithoutMultiKey");

        TimeMeasure.begin("testWithIntegerKeys executeWithMultiKey");
        LoadReadExecutorWithMultiKey executor = new LoadReadExecutorWithMultiKey(ITERATIONS_FOR_QUICK) {

            @Override
            protected Object[] getKeysFromRow(RowStruct row) {
                return new Object[] { row.integerKey };
            }

            @Override
            protected Object[] getKeysFromIndex(int i) {
                return new Object[] { integers.get(i), };
            }

        };
        executeWithMultiKey(executor);

        TimeMeasure.end("testWithIntegerKeys executeWithMultiKey");

    }

    @Test
    public void testWithIntAndMultiKeys() {

        TimeMeasure.begin("testWithIntAndMultiKeys");
        LoadReadExecutorWithMultiKey executor = new LoadReadExecutorWithMultiKey(ITERATIONS_FOR_QUICK) {

            @Override
            protected Object[] getKeysFromRow(RowStruct row) {
                return new Object[] { row.intKey };
            }

            @Override
            protected Object[] getKeysFromIndex(int i) {
                return new Object[] { i, };
            }

        };
        executeWithMultiKey(executor);

        TimeMeasure.end("testWithIntAndMultiKeys");

    }

    @Test
    public void testWithIntKey() {

        TimeMeasure.begin("testWithIntKeys");
        LoadReadExecutorWithMultiKey executor = new LoadReadExecutorWithMultiKey(ITERATIONS_FOR_QUICK) {

            @Override
            protected Object[] getKeysFromRow(RowStruct row) {
                return new Object[] { row.intKey };
            }

            @Override
            protected Object[] getKeysFromIndex(int i) {
                return new Object[] { i, };
            }

        };
        executeWithMultiKey(executor);

        TimeMeasure.end("testWithIntKeys");

    }

    @Test
    public void testWithStringIntKeys() {

        TimeMeasure.begin("testWithStringIntKeys");
        LoadReadExecutorWithMultiKey executor = new LoadReadExecutorWithMultiKey(ITERATIONS_FOR_QUICK) {

            @Override
            protected Object[] getKeysFromRow(RowStruct row) {
                return new Object[] { row.stringKey, row.intKey, };
            }

            @Override
            protected Object[] getKeysFromIndex(int i) {
                return new Object[] { String.valueOf(i), i, };
            }

        };
        executeWithMultiKey(executor);

        TimeMeasure.end("testWithStringIntKeys");

    }

    @Test
    public void testWithIntStringKeys() {

        TimeMeasure.begin("testWithIntStringKeys");
        LoadReadExecutorWithMultiKey executor = new LoadReadExecutorWithMultiKey(ITERATIONS_FOR_QUICK) {

            @Override
            protected Object[] getKeysFromRow(RowStruct row) {
                return new Object[] { row.intKey, row.stringKey, };
            }

            @Override
            protected Object[] getKeysFromIndex(int i) {
                return new Object[] { i, String.valueOf(i), };
            }

        };
        executeWithMultiKey(executor);

        TimeMeasure.end("testWithIntStringKeys");

    }

    @Test
    public void testWithIntIntegerKeysAndDefinedArray() {

        TimeMeasure.begin("testWithIntIntegerKeysAndDefinedArray");
        LoadReadExecutorWithMultiKey executor = new LoadReadExecutorWithMultiKey(ITERATIONS_FOR_SLOW) {

            @Override
            protected Object[] getKeysFromRow(RowStruct row) {
                Object[] array = ARRAY_TWO_KEYS;
                array[0] = row.intKey;
                array[1] = row.integerKey;
                return array;
            }

            @Override
            protected Object[] getKeysFromIndex(int i) {
                Object[] array = ARRAY_TWO_KEYS;
                array[0] = i;
                array[1] = integers.get(i);
                return array;
            }

        };
        executeWithMultiKey(executor);

        TimeMeasure.end("testWithIntIntegerKeysAndDefinedArray");

    }

    @Test
    public void testWithIntIntegerKeys() {

        TimeMeasure.begin("testWithIntIntegerKeys");
        LoadReadExecutorWithMultiKey executor = new LoadReadExecutorWithMultiKey(ITERATIONS_FOR_SLOW) {

            @Override
            protected Object[] getKeysFromRow(RowStruct row) {
                return new Object[] { row.intKey, row.integerKey, };
            }

            @Override
            protected Object[] getKeysFromIndex(int i) {
                return new Object[] { i, integers.get(i), };
            }

        };
        executeWithMultiKey(executor);

        TimeMeasure.end("testWithIntIntegerKeys");

    }

    @Test
    public void testWithIntegerIntegerKeys() {

        TimeMeasure.begin("testWithIntegerIntegerKeys");
        LoadReadExecutorWithMultiKey executor = new LoadReadExecutorWithMultiKey(ITERATIONS_FOR_SLOW) {

            @Override
            protected Object[] getKeysFromRow(RowStruct row) {
                return new Object[] { row.integerKey, row.integerKey, };
            }

            @Override
            protected Object[] getKeysFromIndex(int i) {
                Integer integer = integers.get(i);
                return new Object[] { integer, integer, };
            }

        };
        executeWithMultiKey(executor);

        TimeMeasure.end("testWithIntegerIntegerKeys");

    }

    @Test
    public void testWithIntIntKeys() {

        TimeMeasure.begin("testWithIntIntKeys");
        LoadReadExecutorWithMultiKey executor = new LoadReadExecutorWithMultiKey(ITERATIONS_FOR_SLOW) {

            @Override
            protected Object[] getKeysFromRow(RowStruct row) {
                return new Object[] { row.intKey, row.intKey, };
            }

            @Override
            protected Object[] getKeysFromIndex(int i) {
                return new Object[] { i, i, };
            }

        };
        executeWithMultiKey(executor);

        TimeMeasure.end("testWithIntIntKeys");

    }

    @Test
    public void testWithIntegerIntKeys() {

        TimeMeasure.begin("testWithIntegerIntKeys");
        LoadReadExecutorWithMultiKey executor = new LoadReadExecutorWithMultiKey(ITERATIONS_FOR_SLOW) {

            @Override
            protected Object[] getKeysFromRow(RowStruct row) {
                return new Object[] { row.integerKey, row.intKey, };
            }

            @Override
            protected Object[] getKeysFromIndex(int i) {
                return new Object[] { new Integer(i), i, };
            }

        };
        executeWithMultiKey(executor);

        TimeMeasure.end("testWithIntegerIntKeys");

    }

    @Test
    public void testWithStringStringKeys() {

        TimeMeasure.begin("testWithStringStringKeys LoadReadExecutorWithEqualsHashCode");
        LoadReadExecutorWithEqualsHashCode executorEqualsHashCode = new LoadReadExecutorWithEqualsHashCode(ITERATIONS_FOR_QUICK);

        {
            for (int i = 0; i < 10; i++) {
                System.out.println("executeWithStringStringAndHashMap " + i);
                executorEqualsHashCode.executeWithStringStringAndHashMap();
            }

        }
        TimeMeasure.end("testWithStringStringKeys LoadReadExecutorWithEqualsHashCode");

        // TimeMeasure.begin("testWithStringStringKeys executeWithMultiKey");
        // LoadReadExecutorWithMultiKey executor = new LoadReadExecutorWithMultiKey(ITERATIONS_FOR_QUICK) {
        //
        // @Override
        // protected Object[] getKeysFromRow(RowStruct row) {
        // return new Object[] { row.stringKey, row.stringKey2, };
        // }
        //
        // @Override
        // protected Object[] getKeysFromIndex(int i) {
        // String str = strings.get(i);
        //                
        // return new Object[] { str, str, };
        // }
        //
        // };
        // executeWithMultiKey(executor);
        //
        // TimeMeasure.end("testWithStringStringKeys executeWithMultiKey");

        TimeMeasure.begin("testWithStringStringKeys executeWithoutMultiKey");
        LoadReadExecutorWithoutMultiKey executorWithout = new LoadReadExecutorWithoutMultiKey(ITERATIONS_FOR_QUICK);

        System.out.println("executeStringStringAndMultiKeyMap");
        executorWithout.executeStringStringAndMultiKeyMap();

        TimeMeasure.end("testWithStringStringKeys executeWithoutMultiKey");

    }

    // @Test
    // public void testPerformanceBetweenNewInstanceAndGetFromList() {
    //        
    // // int iterations = 297850;
    // int iterations = 50000000;
    //        
    // ArrayList<Integer> localIntegers = new ArrayList<Integer>();
    //
    // for (int i = 0; i < iterations; i++) {
    // localIntegers.add(new Integer(i));
    // }
    //        
    // TimeMeasure.begin("testPerformanceBetweenNewInstanceAndGetFromList new instance");
    // for (int i = 0; i < iterations; i++) {
    // new Integer(i);
    // }
    // TimeMeasure.end("testPerformanceBetweenNewInstanceAndGetFromList new instance");
    //        
    // TimeMeasure.begin("testPerformanceBetweenNewInstanceAndGetFromList get from list");
    // for (int i = 0; i < iterations; i++) {
    // localIntegers.get(i);
    // }
    // TimeMeasure.end("testPerformanceBetweenNewInstanceAndGetFromList get from list");
    //        
    // }

    /**
     * DOC amaumont Comment method "execute".
     * 
     * @param executor
     */
    private void executeWithMultiKey(LoadReadExecutorWithMultiKey executor) {

        System.out.println("\nWithMultiKeyAndHashMap :");
        executor.executeWithMultiKeyAndHashMap();

        System.out.println("\nWithMultiKey :");
        executor.executeWithMultiKey();

        System.out.println("\nWith reuse :");
        executor.executeWithReuse();

    }

    private void executeWithoutMultiKey(LoadReadExecutorWithoutMultiKey executor) {

    }

    /**
     * DOC amaumont Comment method "executeLoadReadTest".
     */
    private static Object[] getKeysArray(short value) {
        switch (value) {
        case 1:
            return ARRAY_ONE_KEY;
        case 2:
            return ARRAY_TWO_KEYS;
        case 3:
            return ARRAY_THREE_KEYS;
        case 4:
            return ARRAY_FOUR_KEYS;
        default:
            throw new IllegalArgumentException();
        }
    }

    /**
     * 
     * DOC amaumont PerfsJavaHash class global comment. Detailled comment <br/>
     * 
     * $Id$
     * 
     */
    private abstract class LoadReadExecutorWithMultiKey {

        private int nIterations;

        /**
         * DOC amaumont LoadReadExecutor constructor comment.
         */
        public LoadReadExecutorWithMultiKey(int nIterations) {
            super();
            this.nIterations = nIterations;
        }

        public void executeWithReuse() {
            ReusableMultiKeyMap tHash = new ReusableMultiKeyMap();

            TimeMeasure.measureActive = true;

            int lstSize = nIterations;
            TimeMeasure.begin("loading");
            for (int i = 0; i < lstSize; i++) {
                RowStruct row = (RowStruct) rowsLookup.get(i);

                ReusableMultiKey multiKey = new ReusableMultiKey(getKeysFromRow(row));

                tHash.put(multiKey, row);
            }
            TimeMeasure.end("loading");

            TimeMeasure.begin("reading");
            int nRowsFound = 0;
            ReusableMultiKey multiKey = new ReusableMultiKey();
            for (int i = 0; i < lstSize; i++) {

                multiKey.setKeys(getKeysFromIndex(i));

                RowStruct row = (RowStruct) tHash.get(multiKey);
                if (row != null) {
                    nRowsFound++;
                }
                // System.out.println(row.name);
            }
            TimeMeasure.end("reading");
            tHash.clear();

            System.out.println("nRowsFound=" + nRowsFound);

        }

        public void executeWithMultiKey() {
            MultiKeyMap tHash = new MultiKeyMap();

            TimeMeasure.measureActive = true;

            int lstSize = nIterations;
            TimeMeasure.begin("loading");
            for (int i = 0; i < lstSize; i++) {
                RowStruct row = (RowStruct) rowsLookup.get(i);

                MultiKey multiKey = new MultiKey(getKeysFromRow(row));

                tHash.put(multiKey, row);

            }
            TimeMeasure.end("loading");

            TimeMeasure.begin("reading");
            int nRowsFound = 0;
            for (int i = 0; i < lstSize; i++) {

                MultiKey multiKey = new MultiKey(getKeysFromIndex(i));

                RowStruct row = (RowStruct) tHash.get(multiKey);
                if (row != null) {
                    nRowsFound++;
                }
                // System.out.println(row.name);
            }
            TimeMeasure.end("reading");
            tHash.clear();

            System.out.println("nRowsFound=" + nRowsFound);

        }

        public void executeWithMultiKeyAndHashMap() {
            Map<MultiKey, RowStruct> tHash = new HashMap<MultiKey, RowStruct>();

            TimeMeasure.measureActive = true;

            int lstSize = nIterations;
            TimeMeasure.begin("loading");
            for (int i = 0; i < lstSize; i++) {
                RowStruct row = (RowStruct) rowsLookup.get(i);

                MultiKey multiKey = new MultiKey(getKeysFromRow(row));

                tHash.put(multiKey, row);

            }
            TimeMeasure.end("loading");

            TimeMeasure.begin("reading");
            int nRowsFound = 0;
            for (int i = 0; i < lstSize; i++) {

                MultiKey multiKey = new MultiKey(getKeysFromIndex(i));

                RowStruct row = (RowStruct) tHash.get(multiKey);
                if (row != null) {
                    nRowsFound++;
                }
                // System.out.println(row.name);
            }
            TimeMeasure.end("reading");
            tHash.clear();

            System.out.println("nRowsFound=" + nRowsFound);

        }

        public void executeWithRemove() {
            MultiKeyMap tHash = new MultiKeyMap();

            TimeMeasure.measureActive = true;

            int lstSize = nIterations;
            ArrayList<RowStruct> localRowsLookup = rowsLookup;
            TimeMeasure.begin("loading");
            for (int i = 0; i < lstSize; i++) {
                RowStruct row = localRowsLookup.get(i);

                tHash.put(row.stringKey, row.stringKey, row);

            }
            TimeMeasure.end("loading");

            TimeMeasure.begin("reading");
            int nRowsFound = 0;
            for (int i = 0; i < lstSize; i++) {

                String str = String.valueOf(i);

                RowStruct row = (RowStruct) tHash.remove(str, str);
                if (row != null) {
                    nRowsFound++;
                }
                // System.out.println(row.name);
            }
            TimeMeasure.end("reading");
            tHash.clear();

            System.out.println("nRowsFound=" + nRowsFound);

        }

        public void executeWithLocalRowsForeach() {
            MultiKeyMap tHash = new MultiKeyMap();

            TimeMeasure.measureActive = true;

            int lstSize = nIterations;
            ArrayList<RowStruct> localRowsLookup = rowsLookup;
            TimeMeasure.begin("executeWithoutMultiKeyWithLocalRowsForeach loading");
            for (RowStruct row : localRowsLookup) {
                tHash.put(row.stringKey, row.stringKey, row);
                if (row.intKey == 10000) {
                    break;
                }
            }
            TimeMeasure.end("executeWithoutMultiKeyWithLocalRowsForeach loading");

            TimeMeasure.begin("reading");
            int nRowsFound = 0;
            for (int i = 0; i < lstSize; i++) {

                String str = String.valueOf(i);

                RowStruct row = (RowStruct) tHash.get(str, str);
                if (row != null) {
                    nRowsFound++;
                }
                // System.out.println(row.name);
            }
            TimeMeasure.end("reading");
            tHash.clear();

            System.out.println("nRowsFound=" + nRowsFound);

        }

        /**
         * DOC amaumont Comment method "getKeysFromIndex".
         * 
         * @param index
         * @return
         */
        protected abstract Object[] getKeysFromIndex(int index);

        /**
         * DOC amaumont Comment method "getKeysFromRow".
         * 
         * @param row
         * @return
         */
        protected abstract Object[] getKeysFromRow(RowStruct row);

    }

    /**
     * 
     * DOC amaumont PerformanceJavaTHash class global comment. Detailled comment <br/>
     * 
     * $Id$
     * 
     */
    private class LoadReadExecutorWithEqualsHashCode {

        private int nIterations;

        /**
         * DOC amaumont LoadReadExecutor constructor comment.
         */
        public LoadReadExecutorWithEqualsHashCode(int nIterations) {
            super();
            this.nIterations = nIterations;
        }

        /**
         * DOC amaumont Comment method "executeWithStringStringAndHashMap".
         */
        public void executeWithStringStringAndHashMap() {
            int lstSize = nIterations;
            Map<RowStruct, RowStruct> tHash = new HashMap<RowStruct, RowStruct>(lstSize);

            TimeMeasure.measureActive = true;

            ArrayList<RowStruct> localRowsLookup = rowsLookup;

            TimeMeasure.begin("loading");
            for (int i = 0; i < lstSize; i++) {
                RowStruct row = (RowStruct) localRowsLookup.get(i);

                if (RowStruct.integerKeyIsKey || RowStruct.intKeyIsKey || !RowStruct.stringKeyIsKey || !RowStruct.stringKey2IsKey) {
                    RowStruct.integerKeyIsKey = false;
                    RowStruct.intKeyIsKey = false;
                    RowStruct.stringKeyIsKey = true;
                    RowStruct.stringKey2IsKey = true;
                }
                tHash.put(row, row);
                // TimeMeasure.step("loading", "end of loop block");
            }
            TimeMeasure.end("loading");

            TimeMeasure.begin("reading");
            int nRowsFound = 0;

            RowStruct rowStructKey = new RowStruct();

            ArrayList<Integer> localIntegers = integers;
            ArrayList<String> localStrings = strings;

            for (int i = 0; i < lstSize; i++) {

                if (RowStruct.integerKeyIsKey || RowStruct.intKeyIsKey || !RowStruct.stringKeyIsKey || !RowStruct.stringKey2IsKey) {
                    RowStruct.integerKeyIsKey = false;
                    RowStruct.intKeyIsKey = false;
                    RowStruct.stringKeyIsKey = true;
                    RowStruct.stringKey2IsKey = true;
                }

                rowStructKey.hashCodeDirty = true;
                rowStructKey.stringKey = localStrings.get(i);
                rowStructKey.stringKey2 = rowStructKey.stringKey;
                // rowStructKey.intKey = i;
                // rowStructKey.stringKey = localStrings.get(i);
                RowStruct row = (RowStruct) tHash.get(rowStructKey);
                if (row != null) {
                    nRowsFound++;
                    // System.out.println(row.name);
                }
            }
            TimeMeasure.end("reading");
            tHash.clear();

            System.out.println("nRowsFound=" + nRowsFound);

        }

        public void executeWithIntegerAndHashMap() {
            int lstSize = nIterations;
            Map<RowStruct, RowStruct> tHash = new HashMap<RowStruct, RowStruct>(lstSize);

            TimeMeasure.measureActive = true;

            ArrayList<RowStruct> localRowsLookup = rowsLookup;

            if (!RowStruct.integerKeyIsKey || RowStruct.intKeyIsKey || RowStruct.stringKeyIsKey || RowStruct.stringKey2IsKey) {
                RowStruct.integerKeyIsKey = true;
                RowStruct.intKeyIsKey = false;
                RowStruct.stringKeyIsKey = false;
                RowStruct.stringKey2IsKey = false;
            }

            TimeMeasure.begin("loading");
            for (int i = 0; i < lstSize; i++) {
                RowStruct row = (RowStruct) localRowsLookup.get(i);

                tHash.put(row, row);
                // TimeMeasure.step("loading", "end of loop block");
            }
            TimeMeasure.end("loading");

            TimeMeasure.begin("reading");
            int nRowsFound = 0;

            RowStruct rowStructKey = new RowStruct();

            ArrayList<Integer> localIntegers = integers;
            ArrayList<String> localStrings = strings;

            for (int i = 0; i < lstSize; i++) {

                if (!RowStruct.integerKeyIsKey || RowStruct.intKeyIsKey || RowStruct.stringKeyIsKey || RowStruct.stringKey2IsKey) {
                    RowStruct.integerKeyIsKey = true;
                    RowStruct.intKeyIsKey = false;
                    RowStruct.stringKeyIsKey = false;
                    RowStruct.stringKey2IsKey = false;
                }

                rowStructKey.hashCodeDirty = true;
                rowStructKey.integerKey = localIntegers.get(i);
                // rowStructKey.intKey = i;
                // rowStructKey.stringKey = localStrings.get(i);
                RowStruct row = (RowStruct) tHash.get(rowStructKey);
                if (row != null) {
                    nRowsFound++;
//                    System.out.println(row.name);
                }
            }
            TimeMeasure.end("reading");
            tHash.clear();

            System.out.println("nRowsFound=" + nRowsFound);

        }

    }

    /**
     * 
     * DOC amaumont PerformanceJavaTHash class global comment. Detailled comment <br/>
     * 
     * $Id$
     * 
     */
    private class LoadReadExecutorWithoutMultiKey {

        private int nIterations;

        /**
         * DOC amaumont LoadReadExecutor constructor comment.
         */
        public LoadReadExecutorWithoutMultiKey(int nIterations) {
            super();
            this.nIterations = nIterations;
        }

        public void executeWithIntAndHashMap() {
            Map<Integer, RowStruct> tHash = new HashMap<Integer, RowStruct>();

            TimeMeasure.measureActive = true;

            int lstSize = nIterations;
            ArrayList<RowStruct> localRowsLookup = rowsLookup;

            TimeMeasure.begin("loading");
            for (int i = 0; i < lstSize; i++) {
                RowStruct row = (RowStruct) localRowsLookup.get(i);

                tHash.put(i, row);

            }
            TimeMeasure.end("loading");

            TimeMeasure.begin("reading");
            int nRowsFound = 0;
            for (int i = 0; i < lstSize; i++) {

                RowStruct row = (RowStruct) tHash.get(i);
                if (row != null) {
                    nRowsFound++;
                }
                // System.out.println(row.name);
            }
            TimeMeasure.end("reading");
            tHash.clear();

            System.out.println("nRowsFound=" + nRowsFound);

        }

        public void executeWithIntegerAndHashMap() {
            Map<Integer, RowStruct> tHash = new HashMap<Integer, RowStruct>();

            TimeMeasure.measureActive = true;

            ArrayList<RowStruct> localRowsLookup = rowsLookup;
            ArrayList<Integer> localIntegers = integers;

            int lstSize = nIterations;

            TimeMeasure.begin("loading");
            for (int i = 0; i < lstSize; i++) {
                RowStruct row = (RowStruct) localRowsLookup.get(i);
                tHash.put(row.integerKey, row);

            }
            TimeMeasure.end("loading");

            TimeMeasure.begin("reading");
            int nRowsFound = 0;
            for (int i = 0; i < lstSize; i++) {

                RowStruct row = (RowStruct) tHash.get(localIntegers.get(i));
                if (row != null) {
                    nRowsFound++;
                }
                // System.out.println(row.name);
            }
            TimeMeasure.end("reading");
            tHash.clear();

            System.out.println("nRowsFound=" + nRowsFound);

        }

        public void executeWithIntegerAndHashedMap() {
            Map tHash = new HashedMap();

            TimeMeasure.measureActive = true;

            ArrayList<RowStruct> localRowsLookup = rowsLookup;
            ArrayList<Integer> localIntegers = integers;

            int lstSize = nIterations;

            TimeMeasure.begin("loading");
            for (int i = 0; i < lstSize; i++) {
                RowStruct row = (RowStruct) localRowsLookup.get(i);
                tHash.put(row.integerKey, row);

            }
            TimeMeasure.end("loading");

            TimeMeasure.begin("reading");
            int nRowsFound = 0;
            for (int i = 0; i < lstSize; i++) {

                RowStruct row = (RowStruct) tHash.get(localIntegers.get(i));
                if (row != null) {
                    nRowsFound++;
                }
                // System.out.println(row.name);
            }
            TimeMeasure.end("reading");
            tHash.clear();

            System.out.println("nRowsFound=" + nRowsFound);

        }

        public void executeWithIntegerAndFastHashMap() {
            Map tHash = new FastHashMap();

            TimeMeasure.measureActive = true;

            ArrayList<RowStruct> localRowsLookup = rowsLookup;
            ArrayList<Integer> localIntegers = integers;

            int lstSize = nIterations;

            TimeMeasure.begin("loading");
            for (int i = 0; i < lstSize; i++) {
                RowStruct row = (RowStruct) localRowsLookup.get(i);
                tHash.put(row.integerKey, row);

            }
            TimeMeasure.end("loading");

            TimeMeasure.begin("reading");
            int nRowsFound = 0;
            for (int i = 0; i < lstSize; i++) {

                RowStruct row = (RowStruct) tHash.get(localIntegers.get(i));
                if (row != null) {
                    nRowsFound++;
                }
                // System.out.println(row.name);
            }
            TimeMeasure.end("reading");
            tHash.clear();

            System.out.println("nRowsFound=" + nRowsFound);

        }

        public void executeWithIntegerAndMapAndMultiThreading(Map tHash) {

            /**
             * 
             * DOC amaumont PerformanceJavaTHash.LoadReadExecutorWithoutMultiKey class global comment. Detailled comment
             * <br/>
             * 
             * $Id$
             * 
             */
            class PutThread extends Thread {

                private int length;

                private int start;

                private ArrayList rows;

                private Map map;

                private boolean ended;

                /**
                 * DOC amaumont PutThread constructor comment.
                 * 
                 * @param localRowsLookup
                 * @param i
                 * @param j
                 */
                public PutThread(Map map, ArrayList rows, int start, int length) {
                    this.map = map;
                    this.rows = rows;
                    this.start = start;
                    this.length = length;
                }

                /*
                 * (non-Javadoc)
                 * 
                 * @see java.lang.Thread#run()
                 */
                @Override
                public void run() {

                    ArrayList localRows = this.rows;
                    int start = this.start;
                    int length = this.length;
                    Map map = this.map;

                    for (int i = start; i < length; i++) {
                        RowStruct row = (RowStruct) localRows.get(i);
                        map.put(row.integerKey, row);
                    }
                }

            }
            ;

            ArrayList<RowStruct> localRowsLookup = rowsLookup;
            ArrayList<Integer> localIntegers = integers;

            int lstSize = nIterations;
            PutThread putThread1 = new PutThread(tHash, localRowsLookup, 0, nIterations / 2);
            PutThread putThread2 = new PutThread(tHash, localRowsLookup, nIterations / 2, nIterations);
            TimeMeasure.begin("loading");

            putThread1.start();
            putThread2.start();

            while (putThread1.isAlive() || putThread2.isAlive())
                ;

            TimeMeasure.end("loading");

            TimeMeasure.begin("reading");
            int nRowsFound = 0;
            for (int i = 0; i < lstSize; i++) {

                RowStruct row = (RowStruct) tHash.get(localIntegers.get(i));
                if (row != null) {
                    nRowsFound++;
                }
                // System.out.println(row.name);
            }
            TimeMeasure.end("reading");
            tHash.clear();

            System.out.println("nRowsFound=" + nRowsFound);

        }

        public void executeStringStringAndMultiKeyMap() {
            MultiKeyMap tHash = new MultiKeyMap();

            TimeMeasure.measureActive = true;

            int lstSize = nIterations;
            ArrayList<RowStruct> localRowsLookup = rowsLookup;
            TimeMeasure.begin("loading");
            for (int i = 0; i < lstSize; i++) {
                RowStruct row = localRowsLookup.get(i);
                tHash.put(row.stringKey, row.stringKey, row);
            }
            TimeMeasure.end("loading");

            ArrayList<String> localStrings = strings;

            TimeMeasure.begin("reading");
            int nRowsFound = 0;
            for (int i = 0; i < lstSize; i++) {

                // String str = String.valueOf(i);
                String str = localStrings.get(i);

                RowStruct row = (RowStruct) tHash.get(str, str);
                if (row != null) {
                    nRowsFound++;
                }
                // System.out.println(row.name);
            }
            TimeMeasure.end("reading");
            tHash.clear();

            System.out.println("nRowsFound=" + nRowsFound);

        }

        public void executeWithLocalRowsInt() {
            MultiKeyMap tHash = new MultiKeyMap();

            TimeMeasure.measureActive = true;

            int lstSize = nIterations;
            ArrayList<RowStruct> localRowsLookup = rowsLookup;
            TimeMeasure.begin("loading");
            for (int i = 0; i < lstSize; i++) {
                RowStruct row = localRowsLookup.get(i);

                tHash.put(row.intKey, row);

            }
            TimeMeasure.end("loading");

            TimeMeasure.begin("reading");
            int nRowsFound = 0;
            for (int i = 0; i < lstSize; i++) {

                String str = String.valueOf(i);

                RowStruct row = (RowStruct) tHash.get(i);
                if (row != null) {
                    nRowsFound++;
                }
                // System.out.println(row.name);
            }
            TimeMeasure.end("reading");
            tHash.clear();

            System.out.println("nRowsFound=" + nRowsFound);

        }

    }

}
