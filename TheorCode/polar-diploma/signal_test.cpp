//
// Created by Vladislav.Kuznetsov on 15.10.2021.
//

#include <memory>
#include <vector>
#include <random>
#include <iostream>
#include "polar/PolarCoder.h"
#include <aff3ct.hpp>

using namespace aff3ct;

struct params {
    int K = 512;     // number of information bits
    int N = 1024;     // codeword size
    int fe = 100;     // number of frame errors
    int seed = 0;     // PRNG seed for the AWGN channel
    std::vector<bool> frozen = std::vector<bool>{true, true, true, true, true, true, true, true, true, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, true, true, true,
                                                 true, true, true, true, true, true, true, false, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, true, true, true,
                                                 true, true, true, true, false, true, true, true, false, true, false,
                                                 false, false, true, true, true, true, true, true, true, true, true,
                                                 true, true, true, true, true, true, false, true, true, true, true,
                                                 true, true, true, false, true, true, true, false, true, false, false,
                                                 false, true, true, true, true, true, true, true, false, true, true,
                                                 true, false, true, false, false, false, true, true, true, false, true,
                                                 false, false, false, true, false, false, false, false, false, false,
                                                 false, true, true, true, true, true, true, true, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, false, true,
                                                 true, true, true, true, true, true, true, true, true, true, true, true,
                                                 true, true, false, true, true, true, true, true, true, true, false,
                                                 true, true, true, false, true, false, false, false, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, true, true, true,
                                                 false, true, true, true, true, true, true, true, false, true, true,
                                                 true, false, true, false, false, false, true, true, true, true, true,
                                                 true, true, false, true, true, true, false, true, false, false, false,
                                                 true, true, true, false, true, false, false, false, false, false,
                                                 false, false, false, false, false, false, true, true, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, true, false,
                                                 true, true, true, true, true, true, true, false, true, true, false,
                                                 false, false, false, false, false, true, true, true, true, true, false,
                                                 false, false, true, false, false, false, false, false, false, false,
                                                 true, false, false, false, false, false, false, false, false, false,
                                                 false, false, false, false, false, false, true, true, true, false,
                                                 true, false, false, false, true, false, false, false, false, false,
                                                 false, false, true, false, false, false, false, false, false, false,
                                                 false, false, false, false, false, false, false, false, true, false,
                                                 false, false, false, false, false, false, false, false, false, false,
                                                 false, false, false, false, false, false, false, false, false, false,
                                                 false, false, false, false, false, false, false, false, false, false,
                                                 true, true, true, true, true, true, true, true, true, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, true, true, true,
                                                 true, true, true, true, true, true, true, false, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, true, true, true,
                                                 false, true, true, true, true, true, true, true, false, true, true,
                                                 true, false, true, false, false, false, true, true, true, true, true,
                                                 true, true, true, true, true, true, true, true, true, false, false,
                                                 true, true, true, false, true, false, false, false, true, false, false,
                                                 false, false, false, false, false, true, true, true, false, true,
                                                 false, false, false, true, false, false, false, false, false, false,
                                                 false, true, false, false, false, false, false, false, false, false,
                                                 false, false, false, false, false, false, false, true, true, true,
                                                 true, true, true, true, false, true, true, true, false, true, false,
                                                 false, false, true, true, true, false, true, false, false, false, true,
                                                 false, false, false, false, false, false, false, true, true, true,
                                                 false, true, false, false, false, true, false, false, false, false,
                                                 false, false, false, true, false, false, false, false, false, false,
                                                 false, false, false, false, false, false, false, false, false, true,
                                                 true, true, false, true, false, false, false, true, false, false,
                                                 false, false, false, false, false, true, false, false, false, false,
                                                 false, false, false, false, false, false, false, false, false, false,
                                                 false, true, false, false, false, false, false, false, false, false,
                                                 false, false, false, false, false, false, false, false, false, false,
                                                 false, false, false, false, false, false, false, false, false, false,
                                                 false, false, false, true, true, true, true, true, true, true, false,
                                                 true, true, true, false, true, false, false, false, true, true, true,
                                                 false, true, false, false, false, true, false, false, false, false,
                                                 false, false, false, true, true, true, false, true, false, false,
                                                 false, true, false, false, false, false, false, false, false, true,
                                                 false, false, false, false, false, false, false, false, false, false,
                                                 false, false, false, false, false, true, true, true, false, true,
                                                 false, false, false, true, false, false, false, false, false, false,
                                                 false, true, false, false, false, false, false, false, false, false,
                                                 false, false, false, false, false, false, false, true, false, false,
                                                 false, false, false, false, false, false, false, false, false, false,
                                                 false, false, false, false, false, false, false, false, false, false,
                                                 false, false, false, false, false, false, false, false, false, true,
                                                 true, true, false, true, false, false, false, true, false, false,
                                                 false, false, false, false, false, true, false, false, false, false,
                                                 false, false, false, false, false, false, false, false, false, false,
                                                 false, true, false, false, false, false, false, false, false, false,
                                                 false, false, false, false, false, false, false, false, false, false,
                                                 false, false, false, false, false, false, false, false, false, false,
                                                 false, false, false, true, false, false, false, false, false, false,
                                                 false, false, false, false, false, false, false, false, false, false,
                                                 false, false, false, false, false, false, false, false, false, false,
                                                 false, false, false, false, false, false, false, false, false, false,
                                                 false, false, false, false, false, false, false, false, false, false,
                                                 false, false, false, false, false, false, false, false, false, false,
                                                 false, false, false, false, false, false, false};
    float ebn0_min = 0.00f; // minimum SNR value
    float ebn0_max = 10.01f; // maximum SNR value
    float ebn0_step = 1.00f; // SNR step
    float R;                   // code rate (R=K/N)
};

void init_params(params &p);

class Polar_decoder : public aff3ct::module::Decoder_repetition<int, float> {

};

struct modules {
    std::unique_ptr<module::Source_random<>> source;
    std::unique_ptr<module::Encoder_polar<>> encoder;
    std::unique_ptr<module::Modem_BPSK<>> modem;
    std::unique_ptr<module::Channel_AWGN_LLR<>> channel;
    std::unique_ptr<module::Decoder_polar_SCL_naive<int, float>> decoder;
    std::unique_ptr<module::Monitor_BFER<>> monitor;
};

void init_modules(const params &p, modules &m);

struct buffers {
    std::vector<int> ref_bits;
    std::vector<int> enc_bits;
    std::vector<float> symbols;
    std::vector<float> noisy_symbols;
    std::vector<float> LLRs;
    std::vector<int> dec_bits;
};

void init_buffers(const params &p, buffers &b);

struct utils {
    std::unique_ptr<tools::Sigma<>> noise;     // a sigma noise type
    std::vector<std::unique_ptr<tools::Reporter>> reporters; // list of reporters dispayed in the terminal
    std::unique_ptr<tools::Terminal_std> terminal;  // manage the output text in the terminal
};

void init_utils(const modules &m, utils &u);

std::vector<int> channel;

int L = 1;

void get_channel() {
    channel.clear();
    std::ifstream in("../frozen.txt");
    int x;
    while (in >> x) {
        channel.push_back(x);
    }
}

void encode(std::vector<int> enter, std::vector<int> &exit) {
    int n = 10;
    int crc_size = 16;
    PolarCode polarCode(n, crc_size, channel);
    std::vector<int> a = polarCode.encode(enter);
    for (int i = 0; i < a.size(); i++) {
        exit[i] = a[i];
    }
}

void decode(std::vector<float> bits, std::vector<int> &returned) {
    int n = 10;
    int crc_size = 16;
    PolarCode polarCode(n, crc_size, channel);
    std::vector<double> probabilities1{};
    std::vector<double> probabilities0{};
    probabilities1.reserve(bits.size());
    probabilities0.reserve(bits.size());
    for (int i: bits) {
        probabilities1.push_back(i);
        probabilities0.push_back(1 - i);
    }
    std::vector<int> decoded = polarCode.decode(probabilities0, probabilities1, L);
    for (int i = 0; i < decoded.size(); i++) {
        returned[i] = decoded[i];
    }
}

int main() {
    get_channel();
    params p;
    init_params(p); // create and initialize the parameters defined by the user
    modules m;
    init_modules(p, m); // create and initialize the modules
    buffers b;
    init_buffers(p, b); // create and initialize the buffers required by the modules
    utils u;
    init_utils(m, u); // create and initialize the utils

    // display the legend in the terminal
    u.terminal->legend();

    // loop over the various SNRs
    for (auto ebn0 = p.ebn0_min; ebn0 < p.ebn0_max; ebn0 += p.ebn0_step) {
        // compute the current sigma for the channel noise
        const auto esn0 = tools::ebn0_to_esn0(ebn0, p.R);
        const auto sigma = tools::esn0_to_sigma(esn0);

        u.noise->set_noise(sigma, ebn0, esn0);

        // update the sigma of the modem and the channel
        m.modem->set_noise(*u.noise);
        m.channel->set_noise(*u.noise);

        // display the performance (BER and FER) in real time (in a separate thread)
        u.terminal->start_temp_report();

        // run the simulation chain
        while (!m.monitor->fe_limit_achieved() && !u.terminal->is_interrupt()) {
            m.source->generate(b.ref_bits);
            encode(b.ref_bits, b.enc_bits);
            m.modem->modulate(b.enc_bits, b.symbols);
            m.channel->add_noise(b.symbols, b.noisy_symbols);
            m.modem->demodulate(b.noisy_symbols, b.LLRs);
            decode(b.LLRs, b.dec_bits);
            m.monitor->check_errors(b.dec_bits, b.ref_bits);
        }

        // display the performance (BER and FER) in the terminal
        u.terminal->final_report();

        // reset the monitor for the next SNR
        m.monitor->reset();
        u.terminal->reset();

        // if user pressed Ctrl+c twice, exit the SNRs loop
        if (u.terminal->is_over()) break;
    }

    std::cout << "# End of the simulation" << std::endl;

    return 0;
}

void init_params(params &p) {
    p.R = (float) p.K / (float) p.N;
}

std::vector<bool> get_frozen();

std::vector<bool> get_frozen_32();

void init_modules(const params &p, modules &m) {
    m.source = std::make_unique<module::Source_random<>>(p.K);
    m.encoder = std::make_unique<module::Encoder_polar<>>(p.K, p.N, p.frozen);
    m.modem = std::make_unique<module::Modem_BPSK<>>(p.N);
    m.channel = std::make_unique<module::Channel_AWGN_LLR<>>(p.N, p.seed);
    m.decoder = std::make_unique<module::Decoder_polar_SCL_naive<int, float>>(p.K, p.N, L, p.frozen);
    m.monitor = std::make_unique<module::Monitor_BFER<>>(p.K, p.fe);
}

void init_buffers(const params &p, buffers &b) {
    b.ref_bits = std::vector<int>(p.K);
    b.enc_bits = std::vector<int>(p.N);
    b.symbols = std::vector<float>(p.N);
    b.noisy_symbols = std::vector<float>(p.N);
    b.LLRs = std::vector<float>(p.N);
    b.dec_bits = std::vector<int>(p.K);
}

void init_utils(const modules &m, utils &u) {
    // create a sigma noise type
    u.noise = std::make_unique<tools::Sigma<>>();
    // report the noise values (Es/N0 and Eb/N0)
    u.reporters.push_back(std::unique_ptr<tools::Reporter>(new tools::Reporter_noise<>(*u.noise)));
    // report the bit/frame error rates
    u.reporters.push_back(std::unique_ptr<tools::Reporter>(new tools::Reporter_BFER<>(*m.monitor)));
    // report the simulation throughputs
    u.reporters.push_back(std::unique_ptr<tools::Reporter>(new tools::Reporter_throughput<>(*m.monitor)));
    // create a terminal that will display the collected data from the reporters
    u.terminal = std::make_unique<tools::Terminal_std>(u.reporters);
}

double normalize(double x) {
    if (x > 1) return 1;
    if (x < 0) return 0;
    return x;
}

bool random_test(std::uniform_int_distribution<int> codeGenerator, std::uniform_real_distribution<double> distribution,
                 std::mt19937 rnd, std::vector<int> new_channel) {
    int n = 10;
    int info_length = (1 << (n - 1));
    int crc_size = 16;
    PolarCode polarCode(n, crc_size, std::move(new_channel));
    std::vector<int> read;
    read.reserve(info_length);
    for (int i = 0; i < info_length; i++) {
        read.push_back(codeGenerator(rnd));
    }
    std::vector<int> a = polarCode.encode(read);
    std::vector<double> probabilities1{};
    std::vector<double> probabilities0{};
    probabilities1.reserve(a.size());
    probabilities0.reserve(a.size());
    for (uint8_t i: a) {
        probabilities0.push_back(i + distribution(rnd));
        probabilities1.push_back(1 - distribution(rnd) - i);
    }
    std::vector<int> decoded = polarCode.decode(probabilities1, probabilities0, L);
    int count = 0;
    for (int i = 0; i < decoded.size(); i++) {
        if (decoded[i] != read[i]) {
            count++;
        }
    }
    return count > 0;
}

double count(std::uniform_int_distribution<int> codeGenerator, double wrongs, std::mt19937 rnd) {
    int wrong_trying = 0;
    int complete = 10;
    std::uniform_real_distribution<double> distribution(0, wrongs);
    for (int i = 0; i < complete; i++) {
        wrong_trying += (random_test(codeGenerator, distribution, rnd, channel)) ? 1 : 0;
    }
    return (wrong_trying + .0) / complete;
}

std::vector<bool> get_frozen() {
    std::vector<bool> frozen(2048, true);
    for (int i = 0; i < 1024 + 256; i++) {
        frozen[channel[i]] = false;
    }
    return frozen;
}

std::vector<bool> get_frozen_32() {
    return std::vector<bool>{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1,
                             1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                             1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                             1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                             1, 1, 1, 1, 1, 1, 1, 1};
}

void wrong_testing() {
    std::uniform_int_distribution<int> dist(0, 1);
    get_channel();
    std::mt19937 rnd;
    for (int wrong = 1; wrong <= 100; wrong += 1) {
        double wrongs = count(dist, (wrong + .0) / 100, rnd);
        std::cout << wrongs << std::endl;
    }
}