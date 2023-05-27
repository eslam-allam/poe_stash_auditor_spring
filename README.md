# poe_stash_auditor_spring
poe stash auditor server rewritten in springBoot

## Local configurations

Reroute port 443 to 8080

    sudo iptables -t nat -A OUTPUT -o lo -p tcp --dport 443 -j REDIRECT --to-port 8080