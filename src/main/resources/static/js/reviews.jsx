const { useEffect, useState } = React;

function Reviews({
    bookId,
    canReview,
    showLoginMessage
}) {
    const [reviews, setReviews] = useState([]);
    const [text, setText] = useState("");
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState("");

    const apiUrl = `/api/books/${bookId}/reviews`;

    function loadReviews() {
        setLoading(true);
        setError("");

        fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error(
                        "Errore nel caricamento delle recensioni"
                    );
                }

                return response.json();
            })
            .then(data => {
                setReviews(data);
            })
            .catch(error => {
                setError(error.message);
            })
            .finally(() => {
                setLoading(false);
            });
    }

    useEffect(() => {
        loadReviews();
    }, [bookId]);

    function handleSubmit(event) {
        event.preventDefault();

        const normalizedText = text.trim();

        if (normalizedText.length < 5) {
            setError(
                "La recensione deve contenere almeno 5 caratteri"
            );
            return;
        }

        const csrfToken = document
            .querySelector('meta[name="_csrf"]')
            ?.getAttribute("content");

        const csrfHeader = document
            .querySelector('meta[name="_csrf_header"]')
            ?.getAttribute("content");

        const headers = {
            "Content-Type": "application/json"
        };

        if (csrfToken && csrfHeader) {
            headers[csrfHeader] = csrfToken;
        }

        setSaving(true);
        setError("");

        fetch(apiUrl, {
            method: "POST",
            headers: headers,
            body: JSON.stringify({
                text: normalizedText
            })
        })
            .then(async response => {
                if (
                    response.status === 401 ||
                    response.status === 403
                ) {
                    throw new Error(
                        "Devi effettuare il login per recensire"
                    );
                }

                if (!response.ok) {
                    const responseBody = await response
                        .json()
                        .catch(() => null);

                    throw new Error(
                        responseBody?.message ||
                        "Impossibile salvare la recensione"
                    );
                }

                return response.json();
            })
            .then(savedReview => {
                setReviews(currentReviews => [
                    savedReview,
                    ...currentReviews
                ]);

                setText("");
            })
            .catch(error => {
                setError(error.message);
            })
            .finally(() => {
                setSaving(false);
            });
    }

    return (
        <section>
            {loading && (
                <p>Caricamento recensioni...</p>
            )}

            {error && (
                <p style={{ color: "red" }}>
                    {error}
                </p>
            )}

            {!loading && reviews.length === 0 && (
                <p>Nessuna recensione presente.</p>
            )}

            {!loading && reviews.length > 0 && (
                <ul>
                    {reviews.map(review => (
                        <li key={review.id}>
                            <p>
                                <strong>
                                    {review.username}
                                </strong>
                            </p>

                            <p>
                                {review.text}
                            </p>

                            <small>
                                {review.creationDate}
                            </small>
                        </li>
                    ))}
                </ul>
            )}

            {canReview && (
                <>
                    <hr />

                    <h3>Scrivi una recensione</h3>

                    <form onSubmit={handleSubmit}>
                        <div>
                            <label htmlFor="review-text">
                                Recensione
                            </label>

                            <br />

                            <textarea
                                id="review-text"
                                value={text}
                                onChange={event =>
                                    setText(event.target.value)
                                }
                                rows="6"
                                cols="60"
                                maxLength="1000"
                                required
                            />
                        </div>

                        <br />

                        <button
                            type="submit"
                            disabled={saving}
                        >
                            {saving
                                ? "Salvataggio..."
                                : "Salva recensione"}
                        </button>
                    </form>
                </>
            )}

            {!canReview && showLoginMessage && (
                <p>
                    <a href="/login">
                        Accedi
                    </a>
                    {" "}per scrivere una recensione.
                </p>
            )}
        </section>
    );
}

const rootElement =
    document.getElementById("reviews-root");

if (rootElement) {
    const bookId =
        rootElement.dataset.bookId;

    const canReview =
        rootElement.dataset.canReview === "true";

    const showLoginMessage =
        rootElement.dataset.showLoginMessage === "true";

    const root =
        ReactDOM.createRoot(rootElement);

    root.render(
        <Reviews
            bookId={bookId}
            canReview={canReview}
            showLoginMessage={showLoginMessage}
        />
    );
}