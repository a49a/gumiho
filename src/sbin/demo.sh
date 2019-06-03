function main() {
    send
}

function send() {
    subject="gumiho"
    to_addr="rsl4@foxmail.com"
    mail -s $subject $to_addr
}

main
