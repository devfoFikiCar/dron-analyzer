import sys

file1 = open(str(sys.argv[1]), 'r')
file2 = open('2.txt', 'w')
count = 0

while True:
    count += 1
    line = file1.readline()
    if not line:
        break
    s = line.split(",")
    n = 0
    for l in s:
        if n == 10:
            rs = l
        elif n == 12:
            file2.write(l + " " + rs + " ")
        elif n == 13:
            file2.write(l + "\n")
        n += 1
    print("Line{}: {}".format(count, line.strip()))

file1.close()
