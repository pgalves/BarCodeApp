import re


# Parse the log line with a regex expression
def parse_kurento(line):
    regex = re.compile("\[(?P<date>\d{4}-\d{2}-\d{2})\s(?P<time>\d{2}:\d{2}:\d{2}.\d+)\]\s\S*\s\[(?P<level>\w+)\]\s*(?P<kurentosrc>\S*)\s(?P<kurentoclass>\S*)\s(?P<kurentomethod>\S*)\s((?P<procMsgType>(\w+|\w+\s\w+)):\s\>*(?P<msgjson>.+)|(?P<msg>.*))")
    r = regex.search(line)
    result_set = {}
    if r:
        result_set = r.groupdict()
        return result_set
