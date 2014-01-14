/*
 * The MIT License
 *
 * Copyright 2014 Yann D'Isanto.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.netbeans.modules.nbmongo.util;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.Exceptions;

/**
 *
 * @author Yann D'Isanto
 */
public final class Importer implements Runnable {

    private final DB db;

    private final ImportProperties properties;

    private final Reader reader;

    public Importer(DB db, ImportProperties properties, Reader reader) {
        this.db = db;
        this.properties = properties;
        this.reader = reader;
    }
    

    @Override
    public void run() {
        final DBCollection collection = db.getCollection(properties.getCollection());
        final BufferedReader br = new BufferedReader(reader);
        try {
            String line;
            while ((line = br.readLine()) != null) {
                collection.insert(parseLine(line));
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    private List<DBObject> parseLine(String line) {
        final Object obj = JSON.parse(line);
        if(obj instanceof List) {
            return (List<DBObject>) obj;
        }
        final List<DBObject> list = new ArrayList<>(1);
        list.add((DBObject) obj);
        return list;
    }

}
