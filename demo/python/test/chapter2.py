
facebook={}
people=['man1','man2','man3']
attribute=['fullname','age','email']

for man in people:
	thisman={}
	for attri in attribute:
		thisman[attri]=input("Input {} for man {}".format(attri,man))
	facebook[man]=thisman

print(facebook)
