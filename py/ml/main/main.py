import torch
import re
import logging
logging.basicConfig(level=logging.DEBUG)
x = torch.eye(3)
print(x)

r=re.match('sys_apply', 'sys_apply')
print(r)

logging.debug("a")