from plugins.outputs.cep_publisher import output_cep_publish
import json


# Process the information extracted from each parsed log line, that matched
# the regular expresion, to generate events that correspond to the ones
# defined in the CEP.
def process_kurento(log_dict):
    if log_dict['msgjson'] is not None:
        log_dict['msg'] = log_dict['msgjson']
        log_dict.pop("msgjson", None)
    else:
        log_dict.pop("msgjson", None)

    if log_dict['kurentomethod'] == "sendEvent()":
        try:
            jsonrpc = json.loads(log_dict['msg'])
            json_data = jsonrpc['params']['value']['data']
            if json_data['codeType'] == "QR-Code":
                qr_event = {'Name': 'KurentoQRcode'}
                qr_event['source'] = json_data['source']
                qr_event['type'] = json_data['type']
                qr_event['value'] = json_data['value']
                output_cep_publish(qr_event)
        except:
            pass
    else:
        log_dict['Name'] = 'KurentoLogs'
        output_cep_publish(log_dict)
