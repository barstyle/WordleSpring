// Ограничиваем ввод до одной буквы и приводим к нижнему регистру
document.querySelectorAll('.fixed-positions input').forEach(input => {
    input.addEventListener('input', function () {
        // Оставляем только первую букву и делаем lowercase
        let val = this.value;
        if (val.length > 1) {
            this.value = val.slice(0, 1);
        }
        // Оставляем только буквы (кириллица и латиница)
        this.value = this.value.toLowerCase().replace(/[^a-zа-яё]/gi, '');
    });
});

function clearInputs() {
    document.getElementById('required').value = '';
    document.getElementById('excluded').value = '';
    for (let i = 0; i < 5; i++) {
        document.getElementById(`pos${i}`).value = '';
    }
    document.getElementById('result').innerHTML = '';
}

async function submitRequest() {
    const required = document.getElementById('required').value.trim().toLowerCase();
    const excluded = document.getElementById('excluded').value.trim().toLowerCase();

    const fixedPositions = {};
    for (let i = 0; i < 5; i++) {
        const val = document.getElementById(`pos${i}`).value.trim();
        if (val && /^[a-zа-яё]$/.test(val)) {
            fixedPositions[i] = val;
        }
    }

    const payload = {
        requiredLetters: required,
        excludedLetters: excluded,
        fixedPositions: fixedPositions
    };

    try {
        const response = await fetch('/api/v1/words', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }

        const words = await response.json();
        const resultDiv = document.getElementById('result');
        if (words && words.length > 0) {
            resultDiv.innerHTML = `<strong>Найдено ${words.length} слов:</strong><br>` + words.join(', ');
        } else {
            resultDiv.innerHTML = '<strong>Совпадений не найдено.</strong>';
        }
    } catch (error) {
        document.getElementById('result').innerHTML =
            `<span class="error">Ошибка при запросе: ${error.message}</span>`;
    }
}

async function resetDictionary() {
    if (!confirm('Вы уверены, что хотите сбросить словарь?')) return;
    try {
        const response = await fetch('/api/v1/words', {
            method: 'DELETE'
        });
        const resultDiv = document.getElementById('result');
        if (response.ok) {
            clearInputs()
            resultDiv.innerHTML = '<strong>✅ Словарь успешно сброшен.</strong>';
        } else {
            throw new Error('Сервер вернул ошибку');
        }
    } catch (error) {
        document.getElementById('result').innerHTML =
            `<span class="error">❌ Ошибка при сбросе: ${error.message}</span>`;
    }
    // Удаляем класс `.active-key` со всех кнопок
    keys.forEach(k => k.classList.remove('active-key'));
}

const keys = document.querySelectorAll('.key');

/**
 * Привязывает виртуальную клавиатуру к контейнеру с классом .input-with-keyboard
 * @param {HTMLElement} container - контейнер, содержащий .keyboard-container и .text-input
 */
function attachKeyboardToContainer(container) {
    const input = container.querySelector('.text-input');
    const keys = container.querySelectorAll('.key');

    if (!input) {
        console.warn('Не найден input в контейнере', container);
        return;
    }

    function handleKeyClick() {
        const keyBtn = this;
        const char = keyBtn.textContent.trim().toLowerCase();

        // Визуальная обратная связь
        keyBtn.classList.add('active-key');
        // setTimeout(() => keyBtn.classList.remove('active-key'), 150);

        // Очистить поле
        if (keyBtn.classList.contains('clear-field')) {
            console.log("CLEAR INPUT TEXT")
            input.value = '';
            input.dispatchEvent(new Event('input', { bubbles: true }));
            // Сбросить подсветку ВСЕХ клавиш в этом контейнере
            container.querySelectorAll('.key').forEach(k => {
                k.classList.remove('active-key');
            });
            return;
        }

        // Backspace
        if (keyBtn.classList.contains('backspace')) {
            console.log("REMOVE LAST LETTER")
            input.value = input.value.slice(0, -1);
            input.dispatchEvent(new Event('input', { bubbles: true }));
            return;
        }

        // Обычная буква (только кириллица)
        if (/^[а-яё]$/.test(char)) {
            input.value += char;
            input.dispatchEvent(new Event('input', { bubbles: true }));
        }
    }

    keys.forEach(key => {
        // Предотвращаем дублирование обработчиков при повторном вызове
        key.removeEventListener('click', handleKeyClick);
        key.addEventListener('click', handleKeyClick);
    });
}

// Автоматически подключаем клавиатуру ко всем существующим блокам на странице
document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.input-with-keyboard').forEach(container => {
        attachKeyboardToContainer(container);
    });
});
