class Dog:
    name = "dahuang"

    def __init__(self, age):
        print("new Dog()")
        self.age = age

    def see(self):
        print("狗看家")

    def add(self, a, b):
        return a + b

    def __del__(self):
        print("对象销毁了")


d = Dog(12)
print(d)
print(Dog.name)
d.see()
print(d.add(11, 2))

delattr(d, "age")
print(hasattr(d, "age"))

dict1 = Dog.__dict__
for key in dict1.keys():
    print(key + ":" + str(dict1[key]))
# del d
d = None
