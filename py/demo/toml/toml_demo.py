import toml
toml_string = ''
with open('./demo.toml', 'r') as f:
    toml_string = f.read()
parsed_toml = toml.loads(toml_string)
for x in parsed_toml['dbs']:
    print(x)