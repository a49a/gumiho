import logging
logger = logging.getLogger('demo')
logging.basicConfig(
    level = logging.DEBUG,
    format = '%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger.debug("hehe")