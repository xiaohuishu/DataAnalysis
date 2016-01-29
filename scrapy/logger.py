import logging
import logging.config

logging.config.fileConfig('logging.conf')
root_logger = logging.getLogger('root')

def getRoot_Logger():
    return root_logger