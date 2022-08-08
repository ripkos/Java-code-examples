package zad1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProgLang {
    private Map<String, Collection<String>> LangMap = new LinkedHashMap<>();

    public ProgLang(String s) throws IOException {
        FileReader fr = new FileReader(s);
        BufferedReader file = new BufferedReader(fr);
        String row = file.readLine();
        while (row != null) {
            String[] arr = row.split("\t");

            List<String> tmplist = new LinkedList<>();
            for (int i = 1; i < arr.length; i++) {
                if(!tmplist.contains(arr[i]))
                tmplist.add(arr[i]);
            }
            LangMap.put(arr[0], tmplist);
            row = file.readLine();
        }
    }

    public Map<String, Collection<String>> getLangsMap() {
        return new LinkedHashMap<>(LangMap);

    }

    public Map<String, Collection<String>> getProgsMap() {
        Map<String, Collection<String>> map = new LinkedHashMap<>();
        LangMap.forEach((k, v) -> {
            v.forEach(v2 -> {
                Collection<String> tmpList = map.getOrDefault(v2, new LinkedList<>());
                tmpList.add(k);
                map.put(v2, tmpList);
            });
        });
        return map;
    }

    public Map<String, Collection<String>> getLangsMapSortedByNumOfProgs() {
        Comparator<Map.Entry<String, Collection<String>>> comparator = (o1, o2) -> {
            int i = Integer.compare(o1.getValue().size(), o2.getValue().size());
            if (i == 0) {
                i = o1.getKey().compareToIgnoreCase(o2.getKey()) * -1;
            }
            return i * -1;
        };
        return sorted(getLangsMap(), comparator);
    }

    public Map<String, Collection<String>> getProgsMapSortedByNumOfLangs() {
        Comparator<Map.Entry<String, Collection<String>>> comparator = (o1, o2) -> {
            int i = Integer.compare(o1.getValue().size(), o2.getValue().size());
            if (i == 0) {
                i = o1.getKey().compareToIgnoreCase(o2.getKey()) * -1;
            }
            return i * -1;
        };
        return sorted(getProgsMap(), comparator);
    }

    public Map<String, Collection<String>> getProgsMapForNumOfLangsGreaterThan(int i) {
        return filtered(getProgsMap(), (o) -> o.getValue().size() > i);
    }

    public <K, V extends Collection<K>> Map<K, V> sorted(Map<K, V> map, Comparator<Map.Entry<K, V>> comparator) {
        return map.entrySet().stream().sorted(
                comparator
        ).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldV, newV) -> oldV, LinkedHashMap::new));

    }

    public <K, V extends Collection<String>> Map<K, V> filtered(Map<K, V> map, Predicate<Map.Entry<K, V>> predicate) {
        return map.entrySet().stream().filter(predicate).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldV, newV) -> oldV, LinkedHashMap::new));
    }
}

/*
@1 Mapa językow:
Groovy = [Z, Y, X, D]
Java = [V, B, C, D, A, Z]
C++ = [G, J, H]
C# = [P, S, Q, V, D]
Scala = [A, D]
@2 Mapa programistów:
Z = [Groovy, Java]
Y = [Groovy]
X = [Groovy]
D = [Groovy, Java, C#, Scala]
V = [Java, C#]
B = [Java]
C = [Java]
A = [Java, Scala]
G = [C++]
J = [C++]
H = [C++]
P = [C#]
S = [C#]
Q = [C#]
@3 Języki posortowane wg liczby programistów:
Java = [V, B, C, D, A, Z]
C# = [P, S, Q, V, D]
Groovy = [Z, Y, X, D]
C++ = [G, J, H]
Scala = [A, D]
@4 Programiści posortowani wg liczby języków:
D = [Groovy, Java, C#, Scala]
A = [Java, Scala]
V = [Java, C#]
Z = [Groovy, Java]
B = [Java]
C = [Java]
G = [C++]
H = [C++]
J = [C++]
P = [C#]
Q = [C#]
S = [C#]
X = [Groovy]
Y = [Groovy]
@5 Oryginalna mapa języków niezmieniona:
Groovy = [Z, Y, X, D]
Java = [V, B, C, D, A, Z]
C++ = [G, J, H]
C# = [P, S, Q, V, D]
Scala = [A, D]
@6 Oryginalna mapa programistów niezmienione:
Z = [Groovy, Java]
Y = [Groovy]
X = [Groovy]
D = [Groovy, Java, C#, Scala]
V = [Java, C#]
B = [Java]
C = [Java]
A = [Java, Scala]
G = [C++]
J = [C++]
H = [C++]
P = [C#]
S = [C#]
Q = [C#]
@7 Mapa programistów znających więcej niż 1 język:
Z = [Groovy, Java]
D = [Groovy, Java, C#, Scala]
V = [Java, C#]
A = [Java, Scala]
@8 Oryginalna mapa programistów nie jest zmieniona:
Z = [Groovy, Java]
Y = [Groovy]
X = [Groovy]
D = [Groovy, Java, C#, Scala]
V = [Java, C#]
B = [Java]
C = [Java]
A = [Java, Scala]
G = [C++]
J = [C++]
H = [C++]
P = [C#]
S = [C#]
Q = [C#]
 */